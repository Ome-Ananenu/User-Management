package com.project.interviewchallenge.user.services;

import com.project.interviewchallenge.user.requests.UserRequests;
import com.project.interviewchallenge.user.response.UserResponse;

public interface UserService {

    UserResponse createUser(UserRequests request);

    UserResponse updateUserDetails(long id, UserRequests request);
}
