package com.binance.connector.client;

import okhttp3.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class MarketWebSocketPool {
    private static final Logger LOGGER = Logger.getLogger(BinanceWebSocketPool.class.getName());
    private static final int MAX_CONNECTIONS = 10;
    private static final long RECONNECT_INTERVAL_SECONDS = 5;
    private static final long PING_INTERVAL_SECONDS = 30;
    private final String wsUrl;
    private final OkHttpClient client;
    private final List<WebSocketConnection> connections;
    private final ScheduledExecutorService scheduler;
    private final Gson gson;
    private boolean isRunning;
    private final Object lock = new Object();
    private DataHandler.MessageCallback messageCallback;

    public BinanceWebSocketPool(String wsUrl) {
        this.wsUrl = wsUrl;
        this.client = new OkHttpClient.Builder().build();
        this.connections = new ArrayList<>();
        this.scheduler = Executors.newScheduledThreadPool(2);
        this.gson = new Gson();
        this.isRunning = false;
    }

    public void start() {
        synchronized (lock) {
            if (isRunning) return;
            isRunning = true;
            // 初始化一个连接，动态扩展
            addConnection();
            startPing();
        }
    }

    public void stop() {
        synchronized (lock) {
            isRunning = false;
            for (WebSocketConnection conn : connections) {
                conn.close();
            }
            connections.clear();
            scheduler.shutdown();
        }
    }

    private void addConnection() {
        synchronized (lock) {
            if (connections.size() >= MAX_CONNECTIONS) {
                LOGGER.warning("Max connections reached: " + MAX_CONNECTIONS);
                return;
            }
            WebSocketConnection conn = new WebSocketConnection(wsUrl, client, this);
            connections.add(conn);
            conn.connect();
            LOGGER.info("Added new connection, total: " + connections.size());
        }
    }

    public void subscribe(String streamType, Map<String, Object> params) {
        synchronized (lock) {
            WebSocketConnection conn = getAvailableConnection();
            if (conn == null) {
                if (connections.size() < MAX_CONNECTIONS) {
                    addConnection();
                    conn = connections.get(connections.size() - 1);
                } else {
                    LOGGER.warning("No available connections for subscription");
                    return;
                }
            }
            conn.subscribe(streamType, params);
        }
    }

    public void unsubscribe(String streamType, Map<String, Object> params) {
        synchronized (lock) {
            for (WebSocketConnection conn : connections) {
                if (conn.hasSubscription(streamType, params)) {
                    conn.unsubscribe(streamType, params);
                    break;
                }
            }
        }
    }

    private WebSocketConnection getAvailableConnection() {
        synchronized (lock) {
            for (WebSocketConnection conn : connections) {
                if (conn.getSubscriptionCount() < 1024 && conn.isConnected()) { // 币安单连接最多1024订阅
                    return conn;
                }
            }
            return null;
        }
    }

    void scheduleReconnect(WebSocketConnection conn) {
        if (isRunning) {
            scheduler.schedule(() -> {
                synchronized (lock) {
                    if (connections.contains(conn)) {
                        conn.connect();
                    }
                }
            }, RECONNECT_INTERVAL_SECONDS, TimeUnit.SECONDS);
        }
    }

    private void startPing() {
        scheduler.scheduleAtFixedRate(() -> {
            synchronized (lock) {
                if (isRunning) {
                    for (WebSocketConnection conn : connections) {
                        conn.sendPing();
                    }
                }
            }
        }, PING_INTERVAL_SECONDS, PING_INTERVAL_SECONDS, TimeUnit.SECONDS);
    }

    public void setMessageCallback(DataHandler.MessageCallback callback) {
        this.messageCallback = callback;
    }

    void onMessageReceived(JsonObject data) {
        if (messageCallback != null) {
            messageCallback.onMessage(data);
        }
    }
}