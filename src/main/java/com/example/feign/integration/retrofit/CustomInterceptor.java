package com.example.feign.integration.retrofit;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.StreamUtils;
import retrofit2.Invocation;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.zip.GZIPInputStream;

@Slf4j
public class CustomInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        return cloneResponse(response);
    }

    private Response cloneResponse(Response response) throws IOException {
        Response.Builder clonedResponse = response.newBuilder()
                .request(response.request())
                .code(response.code())
                .headers(removeGzipHeader(response))
                .message(response.message())
                .handshake(response.handshake())
                .protocol(response.protocol());

        if (response.body() == null) {
            return clonedResponse.build();
        }
        byte[] bytes = bodyToByteArray(response);

        log.info("\n{}\n----> {} {}\nHeaders: {}\nBody: {}\n<---- Code: {}\nHeaders: {}\nBody: {}",
                getClassAndMethod(response),
                response.request().method(),
                response.request().url(),
                response.request().headers().toMultimap(),
                response.request().body(),
                response.code() + "",
                response.headers().toMultimap(),
                new String(bytes)
        );

        MediaType mediaType = response.body().contentType() == null ? MediaType.parse("application/json; charset=utf-8") : response.body().contentType();
        ResponseBody clonedBody = ResponseBody.create(bytes, mediaType);

        return clonedResponse.body(clonedBody).build();
    }

    private String getClassAndMethod(Response response) {
        Invocation tag = response.request().tag(Invocation.class);
        if (tag == null) {
            return "";
        }
        Method method = tag.method();
        return "%s.%s()".formatted(method.getDeclaringClass().getSimpleName(), method.getName());
    }

    private byte[] bodyToByteArray(Response response) throws IOException {
        InputStream inputStream;
        String encoding = response.header("Content-Encoding");
        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
            inputStream = new GZIPInputStream(response.body().byteStream());
        } else {
            inputStream = response.body().byteStream();
        }
        return StreamUtils.copyToByteArray(inputStream);
    }

    private static Headers removeGzipHeader(Response response) {
        Headers headers = response.headers();
        Headers.Builder clonedHeaders = new Headers.Builder();
        for (int i = 0, size = headers.size(); i < size; i++) {
            String name = headers.name(i);
            String value = headers.value(i);
            if (!"Content-Encoding".equalsIgnoreCase(name)) {
                clonedHeaders.add(name, value);
            }
        }
        return clonedHeaders.build();
    }
}


