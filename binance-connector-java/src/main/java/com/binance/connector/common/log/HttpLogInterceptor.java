package com.binance.connector.common.log;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@Slf4j
public class HttpLogInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {

        Request request = chain.request();
        // 包装request后，获取请求头
        // 异步一定要包装，同步请求自然会有；异步请求拦截器执行在前，无traceId
//        String traceIdHeader = request.header(TRACE_ID_HEADER);
//        if (StringUtils.isNotBlank(traceIdHeader)) {
//            TraceIdUtil.setLogTraceId(traceIdHeader);
//        }
        long start = System.currentTimeMillis();
        Response response = chain.proceed(request);
        long time = System.currentTimeMillis() - start;
        log.info("OkHttp invoke url[{}], header[{}] status[{}], time[{}]ms", request.url(), request.headers().toString(), response.code(), time);
        return response;
    }
}
