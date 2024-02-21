package com.example.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubUser(
        @JsonProperty("login")
        String myLogin,
        long id,
        String nodeId,
        String avatarUrl,
        String url,
        String htmlUrl,
        String followersUrl,
        String followingUrl,
        String gistsUrl,
        String starredUrl,
        String subscriptionsUrl,
        String organizationsUrl,
        String reposUrl,
        String eventsUrl,
        String receivedEventsUrl,
        String type,
        boolean siteAdmin,
        String name,
        String company,
        String blog,
        String location,
        String email,
        Boolean hireable,
        String bio,
        String twitterUsername,
        int publicRepos,
        int publicGists,
        int followers,
        int following,
        String createdAt,
        String updatedAt
) { }
