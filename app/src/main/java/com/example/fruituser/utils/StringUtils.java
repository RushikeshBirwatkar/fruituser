package com.example.fruituser.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_MOBILE_NUMBER = Pattern.compile("(0/91)?[7-9][0-9]{9}");

    public static boolean validateEmailAddress(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public static boolean validateMobileNumber(String mobileNumber) {

        Matcher matcher = VALID_MOBILE_NUMBER.matcher(mobileNumber);
        return (matcher.find() && matcher.group().equals(mobileNumber));
    }
}
