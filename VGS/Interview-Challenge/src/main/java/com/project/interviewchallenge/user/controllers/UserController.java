package com.project.interviewchallenge.user.controllers;

import com.project.interviewchallenge.user.constants.AppConstants;
import com.project.interviewchallenge.user.requests.UserRequests;
import com.project.interviewchallenge.user.response.UserResponse;
import com.project.interviewchallenge.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(AppConstants.API_VERSION_1 + UserController.URL)
@RequiredArgsConstructor
public class UserController {

    public static final String URL = "/user";
    private final UserService userService;

   @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequests requests){

       return new ResponseEntity(userService.createUser(requests), HttpStatus.ACCEPTED);
   }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<UserResponse> updateUserDetails(@PathVariable long id, @Valid @RequestBody UserRequests requests){

        return new ResponseEntity(userService.updateUserDetails(id, requests), HttpStatus.ACCEPTED);
    }

}
