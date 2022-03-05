package com.project.interviewchallenge.user.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static String stripPackageNames(String rawInput) {
        if (StringUtils.isBlank(rawInput)) {
            return "";
        } else if (StringUtils.remove(rawInput, " ").equals(rawInput)) {
            return rawInput;
        } else {
            String pattern = "(\\w+\\.\\w+\\.\\w+)";
            Pattern packageNamePattern = Pattern.compile(pattern);
            Matcher matcher = packageNamePattern.matcher(rawInput);
            return matcher.replaceAll("");
        }
    }
}
