package com.project.interviewchallenge.user.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.project.interviewchallenge.user.constants.AppConstants;
import com.project.interviewchallenge.user.controllers.UserController;
import com.project.interviewchallenge.user.entities.User;
import com.project.interviewchallenge.user.repository.UserRepository;


import com.project.interviewchallenge.user.requests.UserRequests;
import com.project.interviewchallenge.user.services.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.xml.bind.ValidationException;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    private final Faker faker = new Faker();

    private final String baseUrl = AppConstants.API_VERSION_1 + UserController.URL;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Test
    public void whenUpdateUserDetailsWithValidPayloadShouldPass() throws Exception{
        User user = new User();
        user.setUsername(faker.name().username().replaceAll("[^A-Za-z]", ""));
        user.setDateOfBirth(LocalDate.now().minusYears(20));
        userRepository.save(user);

        String newUsername = faker.name().username().replaceAll("[^A-Za-z]", "");

        UserRequests request = UserRequests.builder()
                .username(newUsername)
                .dateOfBirth(user.getDateOfBirth())
                .build();

        mockMvc.perform(put(baseUrl+"/"+ user.getId()).content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void whenUpdateUserDetailsWithInValidUsernameShouldFail() throws Exception{
        User user = new User();
        user.setUsername("barbara 123");
        user.setDateOfBirth(LocalDate.now().minusYears(20));
        userRepository.save(user);

        String newUsername = "barb 1283";

        UserRequests request = UserRequests.builder()
                .username(newUsername)
                .dateOfBirth(user.getDateOfBirth())
                .build();

        mockMvc.perform(put(baseUrl + "/" + user.getId()).content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void whenUpdateUserDetailsWithExistingUsernameShouldPass() throws Exception{
        User user = creatUser();

        UserRequests request = UserRequests.builder()
                .username(user.getUsername())
                .dateOfBirth(user.getDateOfBirth())
                .build();

        mockMvc.perform(put(baseUrl + "/" + user.getId()).content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void whenUpdateUserDetailsWithExistingUsernameShouldFail() throws Exception{
        User user1 = creatUser();
        User user2 = creatUser();

        UserRequests request = UserRequests.builder()
                .username(user1.getUsername())
                .dateOfBirth(user1.getDateOfBirth())
                .build();


        mockMvc.perform(put(baseUrl + "/" + user2.getId()).content(objectMapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andDo(MockMvcResultHandlers.print());

    }

    private User creatUser(){
        User createUser = new User();
        createUser.setUsername(faker.name().username().replaceAll("[^A-Za-z]", ""));
        createUser.setDateOfBirth(LocalDate.now().minusYears(20));
        return userRepository.save(createUser);
    }

}
