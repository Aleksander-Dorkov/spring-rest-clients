package com.example.feign;

import com.example.feign.dto.GitHubUser;
import com.example.feign.integration.feign.ApiClient;
import com.example.feign.integration.feign.GitHubClient;
import com.example.feign.integration.retrofit.GitHubServiceRetrofit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import retrofit2.Response;

import java.io.IOException;

@EnableFeignClients
@SpringBootApplication
public class FeignApplication {

    public static void main(String[] args) throws IOException {
        var a = SpringApplication.run(FeignApplication.class, args);
        ApiClient apiClient = a.getBean(ApiClient.class);
        GitHubClient githubClient = a.getBean(GitHubClient.class);
        GitHubServiceRetrofit gitHubServiceRetrofit = a.getBean(GitHubServiceRetrofit.class);
        try {
//            var a1 = apiClient.getUserDetails(new ApplicationUser("aa", 12));
//            var a2 = githubClient.getUser("Aleksander-Dorkov");
//            var a3 = githubClient.getUserRepos("Aleksander-Dorkov");
//            var a4 = apiClient.testRecordWithCustomMapping(new TestRecord("11", "22"));
//            System.out.println("\n\not koda \n" + a1);
//            System.out.println(a2);
//            System.out.println(a3);
//            System.out.println(a4);
        } catch (Exception e) {
            System.out.println(e);
        }

//
        Response<GitHubUser> resp = gitHubServiceRetrofit.getUser("Aleksander-Dorkov").execute();
        System.out.println(resp.body());
    }
}
