package com.example.feign.integration.restclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.StreamUtils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Slf4j
@Configuration
public class RestClientConfig {

    @Bean(name = "custom_logging_interceptor")
    public ClientHttpRequestInterceptor loggingInterceptor() {
        return (request, body, execution) -> {
            ClientHttpResponse response = execution.execute(request, body);
            byte[] bytes = StreamUtils.copyToByteArray(response.getBody());

            log.info("\n****HTTP Request****\n----> {} {}\nHeaders: {}\nBody: {}\n<---- Code: {}\nHeaders: {}\nBody: {}",
                    request.getMethod(),
                    request.getURI(),
                    request.getHeaders(),
                    new String(body),
                    response.getStatusCode(),
                    response.getHeaders(),
                    new String(bytes));
            return response;
        };
    }

    @Bean(name = "custom_timeout_factory")
    public BufferingClientHttpRequestFactory restClientFactory() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setReadTimeout(Duration.of(1, ChronoUnit.SECONDS));
        simpleClientHttpRequestFactory.setConnectTimeout(Duration.of(1, ChronoUnit.SECONDS));
        return new BufferingClientHttpRequestFactory(simpleClientHttpRequestFactory);
    }
}
