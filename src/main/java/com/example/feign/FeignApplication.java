package com.example.feign;

import com.example.feign.dto.ApplicationUser;
import com.example.feign.integration.ApiClient;
import com.example.feign.integration.GitHubClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FeignApplication {

    public static void main(String[] args) {
        var a = SpringApplication.run(FeignApplication.class, args);
        ApiClient apiClient = a.getBean(ApiClient.class);
        GitHubClient githubClient = a.getBean(GitHubClient.class);
        try {
            var a1 = apiClient.getUserDetails(new ApplicationUser("aa", 12));
            var a2 = githubClient.getUser("Aleksander-Dorkov");
            var a3 = githubClient.getUserRepos("Aleksander-Dorkov");
            System.out.println("\n\not koda " + a1);
            System.out.println(a2);
            System.out.println(a3);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
