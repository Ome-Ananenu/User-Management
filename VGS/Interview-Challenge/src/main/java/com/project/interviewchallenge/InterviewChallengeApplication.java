package com.project.interviewchallenge;

import com.project.interviewchallenge.user.constants.AppConstants;
import com.project.interviewchallenge.user.requests.UserRequests;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootApplication(scanBasePackages = AppConstants.BASE_PACKAGE)
@EnableJpaAuditing
public class InterviewChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterviewChallengeApplication.class, args);
    }
}
