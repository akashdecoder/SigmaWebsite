package com.website.sigma.controller;

import com.website.sigma.model.Member;
import com.website.sigma.model.MemberArticle;
import com.website.sigma.model.OpenUser;
import com.website.sigma.model.Roles;
import com.website.sigma.repository.MemberArticleRepository;
import com.website.sigma.repository.MemberRepository;
import com.website.sigma.repository.OpenUserRepository;
import com.website.sigma.repository.RolesRepository;
import com.website.sigma.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Objects;

@Controller
public class PostController {

    @Autowired
    private OpenUserRepository openUserRepository;

    @Autowired
    private MemberArticleRepository memberArticleRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ValidationService validationService;

    @PostMapping("/contributed")
    public String contributeArticle(@Valid OpenUser openUser, BindingResult result, Model model,
                                    HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String introduction = request.getParameter("introduction");
        String body1 = request.getParameter("body1");
        String body2 = request.getParameter("body2");
        String conclusion = request.getParameter("conclusion");

        openUser.setIntroduction(introduction);
        openUser.setBody1(body1);
        if(!body2.isEmpty()) {
            openUser.setBody2(body2);
        }
        openUser.setConclusion(conclusion);

        openUserRepository.save(openUser);
        return "redirect:/open_article";
    }

    @PostMapping("/uploaded")
    public String uploadArticle(@Valid MemberArticle memberArticle, BindingResult result, Model model,
                                HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String introduction = request.getParameter("introduction");
        String body1 = request.getParameter("body1");
        String body2 = request.getParameter("body2");
        String conclusion = request.getParameter("conclusion");

        memberArticle.setIntroduction(introduction);
        memberArticle.setBody1(body1);
        if(!body2.isEmpty()) {
            memberArticle.setBody2(body2);
        }
        memberArticle.setConclusion(conclusion);

        memberArticleRepository.save(memberArticle);
        return "redirect:/member_article";
    }

    @PostMapping("/registered")
    public String registerFaculty(@Valid Member member, BindingResult result, Model model,
                                  HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {
        String c_pass = request.getParameter("c_password");
        String err = validationService.validatePassword(member, c_pass);
        if(!err.isEmpty()) {
            ObjectError error = new ObjectError("validationError", err);
            result.addError(error);
            redirectAttributes.addFlashAttribute("warning", err);
        }
        if(result.hasErrors()) {
            return"redirect:/register";
        }
        Roles roles = rolesRepository.findByRole_name("MEMBER");
        member.addRole(roles);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        memberRepository.save(member);
        redirectAttributes.addFlashAttribute("message", member.getFirstname() + " You are registered. Login" +
                " " +
                "to continue.");
        return "redirect:/register";
    }
}