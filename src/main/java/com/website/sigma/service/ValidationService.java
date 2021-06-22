package com.website.sigma.service;

import com.website.sigma.model.Member;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidationService {
    public String validatePassword(Member member) {
        String message = "";
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(member.getPassword());
        if(matcher.matches() != true) {
            message = "! Password is not in proper format !";
        }
        return message;
    }

    public String validatePasswordFormatByStirng(String password, String c_pass) {
        String message = "";
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        if(matcher.matches() != true) {
            message = "! Password is not in proper format !";
        }
        if(matcher.matches() == true && c_pass.compareTo(password) != 0) {
            message = "! Password does not match !";
        }
        return message;
    }
}
