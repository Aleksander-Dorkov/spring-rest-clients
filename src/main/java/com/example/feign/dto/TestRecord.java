package com.example.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TestRecord(
        @JsonProperty("aaa")
        String name,
        @JsonProperty("bbb")
        String lastName) {

}
