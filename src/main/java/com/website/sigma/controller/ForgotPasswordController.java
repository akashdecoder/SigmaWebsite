package com.website.sigma.controller;

import com.website.sigma.model.Member;
import com.website.sigma.service.UserServices;
import com.website.sigma.service.ValidationService;
import com.website.sigma.utils.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private ValidationService validationService;

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "forgotpassword";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        String email = request.getParameter("email");
        String token = RandomString.make(30);

        try {
            userServices.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
//            sendEmail(email, resetPasswordLink);
            model.addAttribute("message1", resetPasswordLink);
            redirectAttributes.addFlashAttribute("message", "Password Reset Link : " + resetPasswordLink);

        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
            redirectAttributes.addFlashAttribute("warning", "Email not found");
        }
        return "redirect:/forgot_password";
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        Member member = userServices.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (member == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        return "reset_password";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model,
                                       RedirectAttributes redirectAttributes) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        String c_pass = request.getParameter("c_password");

        System.out.println(password);
        String error = validationService.validatePasswordFormatByStirng(password, c_pass);

        if(!error.isEmpty()) {
            redirectAttributes.addFlashAttribute("warning1", "Password mismatch or Invalid Password");
            return"redirect:/reset_password?token="+token;
        }

        Member member = userServices.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (member == null) {
            model.addAttribute("message1", "Invalid Token");
            redirectAttributes.addFlashAttribute("warning1", "Website Error");
            return "redirect:/reset_password";
        } else {
            userServices.updatePassword(member, password);
        }

        return "redirect:/login";
    }
}
