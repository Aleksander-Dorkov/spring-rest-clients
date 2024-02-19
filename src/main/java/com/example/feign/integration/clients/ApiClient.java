package com.example.feign.integration.clients;

import com.example.feign.dto.ApplicationUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ApiClient {

    @PostMapping("/users")
    ApplicationUser getUserDetails(@RequestBody ApplicationUser username);

}
