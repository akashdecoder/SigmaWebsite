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
}
