package com.example.feign.integration;

import feign.Contract;
import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.Response;
import feign.RetryableException;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Configuration
@Import(FeignClientsConfiguration.class)
@RequiredArgsConstructor
public class FeignClientsConfigCustom {

    private static final Request.Options REUEST_OPTIONS = new Request.Options(2, TimeUnit.SECONDS, 10000, TimeUnit.MILLISECONDS, true);
    private static final FeignRequestLogger REQUEST_LOGGER = new FeignRequestLogger();
    private static final FeignErrorDecoder ERROR_DECODER = new FeignErrorDecoder();
    private final Decoder decoder;
    private final Encoder encoder;
    private final Contract contract;

    @Bean
    public ApiClient apiClient() {
        return clientBuilder().target(ApiClient.class, "http://localhost:8080");
    }

    @Bean
    public GitHubClient gitHubClient() {
        return clientBuilder().target(GitHubClient.class, "https://api.github.com");
    }

    public Feign.Builder clientBuilder() {
        return Feign.builder()
                .encoder(encoder)
                .decoder(decoder)
                .contract(contract)
                .logLevel(Logger.Level.FULL)
                .options(REUEST_OPTIONS)
                .logger(REQUEST_LOGGER)
                .errorDecoder(ERROR_DECODER)
                .responseInterceptor(new CustomInterceptor());
    }

    @Slf4j
    private static class FeignRequestLogger extends Logger {

        private static final String EMPTY_BODY = "{}";

        @Override
        protected void log(String configKey, String format, Object... args) {
        }

        @Override
        protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
            String methodInfo = configKey.substring(0, configKey.indexOf("(")).replace("#", "-");
            byte[] bytes = null;
            String requestBody = EMPTY_BODY;
            String responseBody = EMPTY_BODY;
            Request request = response.request();

            if (request.body() != null) {
                requestBody = new String(request.body().clone());
            }
            if (response.body() != null) {
                bytes = StreamUtils.copyToByteArray(response.body().asInputStream());
                responseBody = new String(bytes);
            }

            log.info("\n{}\n----> {} {}\nHeaders: {}\nBody: {}\n<---- Code: {}\nHeaders: {}\nBody: {}",
                    methodInfo,
                    request.httpMethod().toString(),
                    request.url(),
                    request.headers(),
                    requestBody,
                    response.status(),
                    response.headers(),
                    responseBody
            );
            Response clonedResponse = Response.builder()
                    .request(response.request())
                    .body(bytes == null ? new byte[] {} : bytes)
                    .headers(response.headers())
                    .status(response.status())
                    .protocolVersion(response.protocolVersion())
                    .build();
            return super.logAndRebufferResponse(configKey, logLevel, clonedResponse, elapsedTime);
        }
    }

    private static class FeignErrorDecoder implements ErrorDecoder {

        private final ErrorDecoder defaultErrorDecoder = new ErrorDecoder.Default();

        @Override
        public Exception decode(String methodKey, Response response) {
            if (response.status() >= 500) {
                return new RetryableException(
                        response.status(),
                        "500 error has accursed",
                        response.request().httpMethod(),
                        2000L,
                        response.request());
            }
            return defaultErrorDecoder.decode(methodKey, response);
        }
    }
}
