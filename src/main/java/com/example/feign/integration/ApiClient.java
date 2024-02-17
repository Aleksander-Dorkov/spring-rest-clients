package com.example.feign.integration;

import com.example.feign.dto.ApplicationUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "api-client", url = "http://localhost:8080", configuration = FeignClientResponseInterceptor.class)
public interface ApiClient {

    @PostMapping("/users")
    ApplicationUser getUserDetails(@RequestBody ApplicationUser username);
}
