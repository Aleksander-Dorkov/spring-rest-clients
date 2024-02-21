package com.example.feign.integration.retrofit;

import com.example.feign.dto.GitHubUser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubServiceRetrofit {

    @GET("/users/{username}")
    Call<GitHubUser> getUser(@Path("username") String username);
}
