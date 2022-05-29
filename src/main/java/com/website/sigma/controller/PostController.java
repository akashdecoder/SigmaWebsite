package com.website.sigma.controller;

import com.website.sigma.model.*;
import com.website.sigma.repository.*;
import com.website.sigma.security.MemberDetails;
import com.website.sigma.service.FileService;
import com.website.sigma.service.FirebaseService;
import com.website.sigma.service.ValidationService;
import com.website.sigma.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutionException;

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

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private FileService fileService;

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
        return "redirect:/uploadarticle";
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

//    @RequestParam("File") MultipartFile multipartFile
    @PostMapping("/recruitments/registered")
    public String registerUser(@Valid User user, BindingResult result, RedirectAttributes redirectAttributes) throws ExecutionException, InterruptedException, IOException {
        String url = "";

        Random random = new Random();
        long rand = random.nextInt(100000000);

        User user1 = firebaseService.getUser(user.getUsn().toUpperCase());

        if(user1 != null) {
            redirectAttributes.addFlashAttribute("warning", "Already Registered. Contact event organizer for updates.");
            return "redirect:/recruitments";
        }

        user.setUser_id(Long.toString(rand));
        user.setUsn(user.getUsn().toUpperCase());
        user.setBranch(UserUtils.getBranchName(user.getUsn().substring(5,7).toUpperCase()));
        user.setInterest("*****");
        user.setSpecialization("*****");
        user.setWhys("*****");
        user.setWhy("******");
        user.setAbout("******");
        user.setPLanguage("******");
        user.setGroups("******");
        user.setFileUrl("******");


//        FileUser fileUser = new FileUser();
//        fileUser.setMultipartFile(multipartFile);
//        url = fileService.upload(multipartFile, user);
//        user.setFileUrl(url);

        firebaseService.saveUser(user);

        redirectAttributes.addFlashAttribute("message", "Registered.");
        return "redirect:/recruitments";
    }
}