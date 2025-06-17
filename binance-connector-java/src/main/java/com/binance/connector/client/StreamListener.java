package com.binance.connector.client;

import com.binance.connector.common.utils.JacksonUtils;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StreamListener<T> extends WebSocketListener {

    private StreamHandler<T> streamHandler;

    private Class<T> eventClass;


    public StreamListener(StreamHandler<T> streamHandler, Class<T> eventClass) {
        this.streamHandler = streamHandler;
        this.eventClass = eventClass;
    }


    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        super.onOpen(webSocket, response);
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        T resp = JacksonUtils.parse(text, eventClass);
        // 执行流处理器
        streamHandler.onResponse(resp);
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        super.onFailure(webSocket, t, response);
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosing(webSocket, code, reason);
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosed(webSocket, code, reason);
    }
}
