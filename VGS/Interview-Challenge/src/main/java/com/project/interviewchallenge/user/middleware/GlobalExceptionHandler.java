package com.project.interviewchallenge.user.middleware;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.project.interviewchallenge.user.exceptions.BadRequestException;
import com.project.interviewchallenge.user.response.ApiResponse;
import com.project.interviewchallenge.user.response.ResponseBuilderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final ResponseBuilderService responseBuilderService;


    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponse handleBadRequestException(BadRequestException ex, HttpServletRequest httpServletRequest) {
        return responseBuilderService.buildErrorResponse(ex.getMessage(), ex.getMessageArgs(), httpServletRequest);
    }


    private void log(Exception ex) {
        log.error("Global exception handler error: ", ex);
    }
}

