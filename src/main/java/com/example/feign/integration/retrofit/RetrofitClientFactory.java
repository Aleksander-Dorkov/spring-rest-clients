package com.example.feign.integration.retrofit;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class RetrofitClientFactory {

    @Bean
    public GitHubServiceRetrofit gitHubService() {
        return build("https://api.github.com", GitHubServiceRetrofit.class);
    }

    public <T> T build(String url, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new CustomInterceptor())
                .build();
        Retrofit build = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(httpClient)
                .build();
        return build.create(clazz);
    }
}
