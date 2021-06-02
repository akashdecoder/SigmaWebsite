package com.website.sigma.controller;

import com.website.sigma.model.*;
import com.website.sigma.repository.*;
import com.website.sigma.security.MemberDetails;
import com.website.sigma.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import java.util.Iterator;
import java.util.List;
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

    @Autowired
    private MessagesRepository messagesRepository;

    @Autowired
    private QueriesRepository queriesRepository;

    @PostMapping("/contributed")
    public String contributeArticle(@Valid OpenUser openUser, BindingResult result, Model model,
                                    HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String introduction = request.getParameter("introduction");
        String body1 = request.getParameter("body1");
        String body2 = request.getParameter("body2");
        String conclusion = request.getParameter("conclusion");

        String title = openUser.getTitle();
        List<OpenUser> openUsers = openUserRepository.getAllByTitle(title);
        for(OpenUser user : openUsers) {
            if(user.getTitle().equals(title) == true) {
                ObjectError error = new ObjectError("validationError", "Title error");
                result.addError(error);
                redirectAttributes.addFlashAttribute("warning", openUser.getFirstname() + " The title has already " +
                        "been taken. Please drop different title.");
                break;
            }
        }
        if(result.hasErrors()) {
            return "redirect:/open_article";
        }

        openUser.setIntroduction(introduction);
        openUser.setBody1(body1);
        if(!body2.isEmpty()) {
            openUser.setBody2(body2);
        }
        openUser.setConclusion(conclusion);
        redirectAttributes.addFlashAttribute("message", openUser.getFirstname() + " Article has been uploaded. It " +
                        "will be reviewed by the organzation.");
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
//        String c_pass = request.getParameter("c_password");
        String err = validationService.validatePassword(member);
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

    @PostMapping("/sentmessage")
    public String sendMessage(@Valid Messages messages, @AuthenticationPrincipal MemberDetails loggedMember,
                              BindingResult result, Model model,
                              HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {
        String message = request.getParameter("message");

        if(result.hasErrors()) {
            redirectAttributes.addFlashAttribute("warning", "Error occured");
            return"redirect:/memberdashboard";
        }
        Member member = memberRepository.findByEmail(loggedMember.getUsername());
        messages.setNotifications(message);
        messages.setName(member.getFirstname());
        messagesRepository.save(messages);
        redirectAttributes.addFlashAttribute("message", loggedMember.getUsername() + " your message has been sent");
        return "redirect:/memberdashboard";
    }

    @PostMapping("/sentQuery")
    public String sendQuery(@Valid Queries queries, BindingResult result, Model model,
                              HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {
        String message = request.getParameter("query");

        if(result.hasErrors()) {
            redirectAttributes.addFlashAttribute("warning", "Error occured");
            return"redirect:/queries";
        }
        queries.setQuestion(message);
        queries.setStatus("Not Answered");
        queriesRepository.save(queries);
        redirectAttributes.addFlashAttribute("message", " Your query has been submitted. Please check the same page " +
                "after sometime.");
        return "redirect:/queries";
    }
}