package com.example.feign.integration;

import com.example.feign.dto.GitHubUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "github-client", url = "https://api.github.com")
public interface GithubClient {

    @GetMapping("/users/{username}")
    GitHubUser getUserDetails(@PathVariable("username") String username);
}
