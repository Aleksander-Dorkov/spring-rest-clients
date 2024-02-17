package com.example.feign;

import com.example.feign.dto.ApplicationUser;
import com.example.feign.dto.GitHubUser;
import com.example.feign.integration.ApiClient;
import com.example.feign.integration.GithubClient;
import feign.FeignException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FeignApplication {

    public static void main(String[] args) {
        var a = SpringApplication.run(FeignApplication.class, args);
        GithubClient githubClient = a.getBean(GithubClient.class);
        ApiClient apiClient = a.getBean(ApiClient.class);
        try {
            GitHubUser userDetails = githubClient.getUserDetails("Aleksander-Dorkov");
            ApplicationUser aa = apiClient.getUserDetails(new ApplicationUser("aa", 12));
            System.out.println("ot koda");
            System.out.println(aa);
            System.out.println(userDetails);
        } catch (FeignException e) {
            System.out.println(e);
        }
    }

}
