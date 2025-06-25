package com.binance.connector.client;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

import java.util.Map;

@Slf4j
class WebSocketConnection {

    private final String wsUrl;
    private final OkHttpClient client;
    private final BinanceWebSocketPool pool;
    private WebSocket webSocket;
    private final SubscriptionManager subscriptionManager;
    private final DataHandler dataHandler;
    private boolean isConnected;

    public WebSocketConnection(String wsUrl, OkHttpClient client, BinanceWebSocketPool pool) {
        this.wsUrl = wsUrl;
        this.client = client;
        this.pool = pool;
        this.subscriptionManager = new SubscriptionManager();
        this.dataHandler = new DataHandler(pool);
        this.isConnected = false;
    }

    public void connect() {
        Request request = new Request.Builder().url(wsUrl).build();
        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                log.info("WebSocket connected");
                isConnected = true;
                subscriptionManager.resubscribe(WebSocketConnection.this);
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                dataHandler.handleMessage(text);
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                log.info("Received binary message: " + bytes.hex());
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                log.warning("WebSocket closing: " + reason + " (code: " + code + ")");
                webSocket.close(1000, null);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                log.warning("WebSocket closed: " + reason + " (code: " + code + ")");
                isConnected = false;
                pool.scheduleReconnect(WebSocketConnection.this);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                log.severe("WebSocket failure: " + t.getMessage());
                isConnected = false;
                pool.scheduleReconnect(WebSocketConnection.this);
            }
        });
    }

    public void close() {
        if (webSocket != null) {
            webSocket.close(1000, "Connection closed");
        }
        isConnected = false;
    }

    public void send(String message) {
        if (webSocket != null && isConnected) {
            webSocket.send(message);
        } else {
            log.warning("WebSocket not connected, cannot send: " + message);
        }
    }

    public void sendPing() {
        send("{\"ping\":" + System.currentTimeMillis() + "}");
    }

    public void subscribe(String streamType, Map<String, Object> params) {
        subscriptionManager.subscribe(this, streamType, params);
    }

    public void unsubscribe(String streamType, Map<String, Object> params) {
        subscriptionManager.unsubscribe(this, streamType, params);
    }

    public boolean hasSubscription(String streamType, Map<String, Object> params) {
        return subscriptionManager.hasSubscription(streamType, params);
    }

    public int getSubscriptionCount() {
        return subscriptionManager.getSubscriptionCount();
    }

    public boolean isConnected() {
        return isConnected;
    }
}