package com.website.sigma.controller;

import com.website.sigma.model.Member;
import com.website.sigma.model.MemberArticle;
import com.website.sigma.model.OpenUser;
import com.website.sigma.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {


    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/editions")
    public String showEditionsPage() {
        return "editions";
    }

    @GetMapping("/articles")
    public String showArticlesPage() {
        return "openarticle";
    }

    @GetMapping("/open_article")
    public String uploadArticlesPage(Model model) {
        model.addAttribute("openUser", new OpenUser());
        return "uploadopen";
    }

    @GetMapping("/member_article")
    public String showMemberUploadArticlesPage(Model model) {
        model.addAttribute("memberArticle", new MemberArticle());
        return "uploadarticle";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("member", new Member());
        return "register_to_sigmaportal";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "memberlogin";
        }
        return "redirect:/";
    }

    @GetMapping("/logged_out")
    public String loggedOut() {
        return "/";
    }

    @GetMapping("/team")
    public String showTeamPage(Model model) {
        List<Member> teams = memberRepository.findAll();
        model.addAttribute("teams", teams);
        return "team";
    }
}