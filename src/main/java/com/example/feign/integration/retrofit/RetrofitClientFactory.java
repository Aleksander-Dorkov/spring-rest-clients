package com.example.feign.integration.retrofit;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
@RequiredArgsConstructor
public class RetrofitClientFactory {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final RetrofitLoggingInterceptor loggingInterceptor;

    @Bean
    public GitHubServiceRetrofit gitHubService() {
        return build("https://api.github.com", GitHubServiceRetrofit.class);
    }

    public <T> T build(String url, Class<T> clazz) {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor)
                .build();
        Retrofit build = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(JacksonConverterFactory.create(OBJECT_MAPPER))
                .client(httpClient)
                .build();
        return build.create(clazz);
    }
}
