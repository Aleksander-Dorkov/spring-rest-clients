package com.example.feign.integration;

import feign.InvocationContext;
import feign.Request;
import feign.Response;
import feign.ResponseInterceptor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomInterceptor implements ResponseInterceptor {


    @Override
    public Object intercept(InvocationContext invocationContext, Chain chain) throws Exception {
        Response response = invocationContext.response();
        Request request = response.request();

        String requestBody = "Empty";
        if (request.body() != null) {
            requestBody = new String(request.body().clone());
        }
//        log.info("\nRequest:\n{} {}\nHeaders: {}\nBody: {}\nResponse:\nCode: {}\nHeaders: {}\nBody: {}",
//                request.httpMethod().toString(),
//                request.url(),
//                request.headers(),
//                requestBody,
//                response.status(),
//                response.headers(),
//                response.body().length()
//        );
        return chain.next(invocationContext);
    }
}
