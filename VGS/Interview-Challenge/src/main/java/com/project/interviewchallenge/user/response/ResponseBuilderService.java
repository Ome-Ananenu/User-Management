package com.project.interviewchallenge.user.response;

import com.project.interviewchallenge.user.utils.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResponseBuilderService {

    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    public ApiResponse buildErrorResponse(String message, Map<String, String> errors, String[] args, HttpServletRequest httpServletRequest) {
        return buildErrorResponse(ResponseCode.FAILED.getCode(), message, errors, args, httpServletRequest);
    }

    public ApiResponse buildErrorResponse(String message, String[] args, HttpServletRequest httpServletRequest) {
        return buildErrorResponse(message, null, args, httpServletRequest);
    }

    private ApiResponse buildErrorResponse(String code, String message, Map<String, String> errors, String[] args, HttpServletRequest httpServletRequest) {
        ApiResponse response = ApiResponse.builder()
                .success(false)
                .code(code)
                .message(Util.stripPackageNames(message))
                .body(null)
                .errors(errors == null || errors.size() == 0 ? null : errors)
                .args(args)
                .build();

        return resolveMessageKeys(response, httpServletRequest);
    }

    private ApiResponse resolveMessageKeys(ApiResponse baseResponse, HttpServletRequest request) {
        if (request == null) {
            return baseResponse;
        }

        final Locale locale = localeResolver.resolveLocale(request);
        baseResponse.setMessage(resolveKey(baseResponse.getMessage(), baseResponse.getArgs(), locale));
        return baseResponse;
    }

    public String resolveKey(String key, Object[] objects, Locale locale) {
        try {
            return messageSource.getMessage(key, objects, locale);
        } catch (Exception ex) {
            return key;
        }
    }
}

