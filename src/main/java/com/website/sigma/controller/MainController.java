package com.website.sigma.controller;

import com.website.sigma.model.Member;
import com.website.sigma.model.MemberArticle;
import com.website.sigma.model.Messages;
import com.website.sigma.model.OpenUser;
import com.website.sigma.repository.MemberCrudRepository;
import com.website.sigma.repository.MemberRepository;
import com.website.sigma.repository.MessagesRepository;
import com.website.sigma.repository.OpenUserRepository;
import com.website.sigma.security.MemberDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class SortByTier implements Comparator<Member> {
    public int compare(Member a, Member b) {
        return (int) (a.getTier() - b.getTier());
    }
}

@Controller
public class MainController {


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OpenUserRepository openUserRepository;

    @Autowired
    private MemberCrudRepository memberCrudRepository;

    @Autowired
    private MessagesRepository messagesRepository;

    @GetMapping("/")
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/editions")
    public String showEditionsPage() {
        return "editions";
    }

    @GetMapping("/articles")
    public String showArticlesPage(Model model) {
        List<OpenUser> articles = openUserRepository.findAll();
        model.addAttribute("articles", articles);
        return "openarticle";
    }

    @GetMapping("/open_article")
    public String uploadArticlesPage(Model model) {
        model.addAttribute("openUser", new OpenUser());
        return "uploadopen";
    }

    @GetMapping("/memberdashboard")
    public String showMemberDashboard(@AuthenticationPrincipal MemberDetails loggedMember,Model model) {
        model.addAttribute("messages", new Messages());
        List<Messages> chats = messagesRepository.findAll();
        model.addAttribute("chats", chats);
        return "dashboard";
    }

    @GetMapping("/profile")
    public String showMemberProfile(@AuthenticationPrincipal MemberDetails loggedMember,Model model) {
        String email = loggedMember.getUsername();
        List<Member> members = memberRepository.findAllByEmail(email);
        model.addAttribute("members", members);
        return "memberprofile";
    }

    @GetMapping("/uploadarticle")
    public String showMemberUploadArticle(Model model) {
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
        for(Member member : teams) {
            System.out.println(member.getYear());
        }
        Collections.sort(teams, new SortByTier());
        model.addAttribute("teams", teams);
        return "team";
    }

    @GetMapping("/account/{member_id}")
    public String showUpdateForm(@AuthenticationPrincipal MemberDetails loggedMember, Model model) {
        String email = loggedMember.getUsername();
        Member member = memberCrudRepository.getMembersByEmail(email);
        model.addAttribute("member", member);
        return "update_member";
    }
}