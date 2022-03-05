package com.project.interviewchallenge.user.requests;

import com.project.interviewchallenge.user.utils.DateTimeUtils;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequests {

    @NotBlank(message = "username is required")
    private String username;

    @NotNull
    @DateTimeFormat(pattern = DateTimeUtils.DATE_FORMAT)
    private LocalDate dateOfBirth;
}
