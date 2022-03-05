package com.project.interviewchallenge.user.services.impl;

import com.project.interviewchallenge.user.entities.User;
import com.project.interviewchallenge.user.exceptions.BadRequestException;
import com.project.interviewchallenge.user.repository.UserRepository;
import com.project.interviewchallenge.user.requests.UserRequests;
import com.project.interviewchallenge.user.response.UserResponse;
import com.project.interviewchallenge.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse createUser(UserRequests request){
        verifyUsernameContainsOnlyLetters(request.getUsername());
        verifyUserDoesNotExist(request.getUsername());

        User user = User.builder()
                .username(request.getUsername())
                .dateOfBirth(request.getDateOfBirth())
                .build();
        userRepository.save(user);

        return new UserResponse(checkDaysToBirthday(request.getDateOfBirth(), request.getUsername()));
    }

    @Override
    public UserResponse updateUserDetails(long id, UserRequests request) {
        User user = validateUpdateUser(id, request);

        user.setUsername(request.getUsername());
        user.setDateOfBirth(request.getDateOfBirth());
        userRepository.save(user);

        return new UserResponse(checkDaysToBirthday(request.getDateOfBirth(), request.getUsername()));
    }

    private User validateUpdateUser(long id, UserRequests request){
        User user = verifyUserExists(id);
        verifyUsernameContainsOnlyLetters(request.getUsername());
        verifyUserWithNameDoesNotExist(id, request);

        return user;
    }

    private void verifyUserDoesNotExist(String username) {
        Optional<User> userExists = userRepository.findByUsername(username);

        if (userExists.isPresent()) {
            throw new BadRequestException(String.format("User with username: '%s' already exists", username));
        }
    }

    private User verifyUserExists(long id) {

        return userRepository.findById(id).orElseThrow(() -> new ValidationException(String.format("User with id %s does not exist", id)));
    }

    private void verifyUsernameContainsOnlyLetters(String username){

        if(!username.matches("[a-zA-Z]+")){
            throw new BadRequestException(String.format("Username must contain only letters"));
        };
    }

    private void verifyUserWithNameDoesNotExist(long id, UserRequests request){
        Optional<User> userExists = userRepository.findByUsername(request.getUsername());

                userExists.ifPresent(x -> {
                    if (x.getId() != id)
                            throw new BadRequestException(String.format("User with username %s already exists", request.getUsername()));
                });
    }

    private String checkDaysToBirthday(LocalDate dateOfBirth, String username){
        int daysInTheYear = 365;
        LocalDate currentDate = LocalDate.now();

        int currentDateAndMonth = absoluteDay(currentDate.getMonthValue(),currentDate.getDayOfMonth());
        int birthDateAndMonth = absoluteDay(dateOfBirth.getMonthValue(),dateOfBirth.getDayOfMonth());
        int daysToBirthDay = birthDateAndMonth - currentDateAndMonth >= 0 ? birthDateAndMonth - currentDateAndMonth :
                daysInTheYear - (currentDateAndMonth - birthDateAndMonth);

        return currentDateAndMonth == birthDateAndMonth ? String.format("Hello, %s ! Happy Birthday!!", username) :
                String.format("Hello, %s! Your birthday is in %s days",username,daysToBirthDay);
    }

    private int absoluteDay(int month, int day){
        int[] days = {0, 0, 31, 60, 91, 121, 152, 182,
                213, 244, 274, 305, 335};
        return days[month] + day;
    }
}
