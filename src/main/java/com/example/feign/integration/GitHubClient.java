package com.example.feign.integration;

import com.example.feign.dto.GitHubUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface GitHubClient {

    @GetMapping("/users/{username}")
    GitHubUser getUser(@PathVariable("username") String username);

    @GetMapping("/users/{username}/repos")
    List<GitHubRepo> getUserRepos(@PathVariable("username") String username);
}
