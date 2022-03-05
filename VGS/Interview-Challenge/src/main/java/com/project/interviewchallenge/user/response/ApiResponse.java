package com.project.interviewchallenge.user.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiResponse {

    protected boolean success;
    protected String code;
    protected String message;
    private Object body;
    private Map<String, String> errors;

    @JsonIgnore
    private String[] args;
}
