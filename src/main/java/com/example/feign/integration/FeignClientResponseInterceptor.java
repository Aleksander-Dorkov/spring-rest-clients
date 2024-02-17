package com.example.feign.integration;

import feign.InvocationContext;
import feign.Request;
import feign.Response;
import feign.ResponseInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class FeignClientResponseInterceptor implements ResponseInterceptor {

    @Override
    public Object intercept(InvocationContext invocationContext, Chain chain) throws Exception {
        Response response = invocationContext.response();
        Request request = response.request();

        String requestBody = "Empty";
        if (request.body() != null) {
            requestBody = new String(request.body().clone());
        }

        if (response.body() == null) {
            log.info("\nRequest:\n{} {}\nBody: {}\nResponse:\nCode: {}\nHeaders: {}",
                    request.httpMethod().toString(),
                    request.url(),
                    requestBody,
                    response.status(),
                    response.headers()
            );

        } else {
            log.info("\nRequest:\n{} {}\nBody: {}\nResponse:\nCode: {}\nHeaders: {}\nBody: {}",
                    request.httpMethod().toString(),
                    request.url(),
                    requestBody,
                    response.status(),
                    response.headers(),
                    response.body().length()
            );
        }
        return chain.next(invocationContext);
    }
}
