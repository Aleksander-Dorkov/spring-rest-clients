package com.example.feign;

import com.example.feign.integration.feign.ApiClient;
import com.example.feign.integration.feign.GitHubClient;
import com.example.feign.integration.restclient.soap.SoapRequest;
import com.example.feign.integration.restclient.soap.SoapResponse;
import com.example.feign.integration.retrofit.GitHubServiceRetrofit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.client.RestClient;

import java.io.IOException;

@Slf4j
@EnableFeignClients
@SpringBootApplication
public class FeignApplication {

    public static void main(String[] args) throws IOException {
        var context = SpringApplication.run(FeignApplication.class, args);
        ApiClient apiClient = context.getBean(ApiClient.class);
        GitHubClient githubClient = context.getBean(GitHubClient.class);
        GitHubServiceRetrofit gitHubServiceRetrofit = context.getBean(GitHubServiceRetrofit.class);
        RestClient githubRestClient = (RestClient) context.getBean("github_rest_client");
        RestClient soapRestClient = (RestClient) context.getBean("soap_client");
        try {
//            var a1 = apiClient.getUserDetails(new ApplicationUser("aa", 12));
//            var a2 = githubClient.getUser("Aleksander-Dorkov");
//            var a3 = githubClient.getUserRepos("Aleksander-Dorkov");
//            var a4 = apiClient.testRecordWithCustomMapping(new TestRecord("11", "22"));
//            var resp = gitHubServiceRetrofit.getUser("Aleksander-Dorkov").execute();
//            System.out.println("\n\not koda \n" + a1);
//            System.out.println(a2);
//            System.out.println(a3);
//            System.out.println(a4);
//            System.out.println(resp.body());
        } catch (Exception e) {
            System.out.println(e);
        }

//        GitHubUser response = githubRestClient.get()
//                .uri("/users/{username}?aa={first}&bb={second}", "Aleksander-Dorkov", "222", "1111")
//                .retrieve()
//                .body(GitHubUser.class);
//        System.out.println(response);
        var numberToWords = new SoapRequest.NumberToWords(500);
        var body2 = new SoapRequest.SoapBody(numberToWords);
        SoapRequest soapRequest = new SoapRequest(body2);

        SoapResponse result = soapRestClient.post()
                .body(soapRequest)
                .retrieve()
                .body(SoapResponse.class);
        System.out.println(result.getSoapBody().getNumberToWords().getResult());
    }
}
