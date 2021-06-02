package com.website.sigma.controller;

import com.website.sigma.model.Member;
import com.website.sigma.model.Queries;
import com.website.sigma.model.Roles;
import com.website.sigma.repository.MemberRepository;
import com.website.sigma.repository.QueriesRepository;
import com.website.sigma.repository.RolesRepository;
import com.website.sigma.security.MemberDetails;
import com.website.sigma.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class CrudController {

    @Autowired
    private ValidationService validationService;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private QueriesRepository queriesRepository;

    @PostMapping("/updated/{member_id}")
    public String updateMember(Member member, RedirectAttributes redirectAttributes,
                                @AuthenticationPrincipal MemberDetails loggedMember, BindingResult result) {

        loggedMember.setFirstName(member.getFirstname());
        loggedMember.setLastName(member.getLastname());

        if(!member.getPassword().isEmpty()) {
            String err = validationService.validatePassword(member);
            if(!err.isEmpty()) {
                ObjectError error = new ObjectError("validationError", err);
                result.addError(error);
                redirectAttributes.addFlashAttribute("warning", "Password is not in format");
            }
            if(result.hasErrors()) {
                return"redirect:/";
            }
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(member.getPassword());
            member.setPassword(encodedPassword);
        }

        Roles roles;
        roles = rolesRepository.findByRole_name("MEMBER");
        member.addRole(roles);
        memberRepository.save(member);
        redirectAttributes.addFlashAttribute("message", "Your account have been updated");

        return "redirect:/account/"+member.getMember_id();

    }

    @PostMapping("/answered/{q_id}")
    public String answerQuery(Queries queries, HttpServletRequest request, RedirectAttributes redirectAttributes,
                              BindingResult result) {

        String answer = request.getParameter("answer");
        queries.setAnswer(answer);
        if(!answer.isEmpty()) {
            queries.setStatus("Answered");
        }
        if(result.hasErrors()) {
            redirectAttributes.addFlashAttribute("warning", "Some error. Try again.");
            return"redirect:/query/" + queries.getQ_id();
        }
        queriesRepository.save(queries);
        redirectAttributes.addFlashAttribute("message", "Query Answered");
        return "redirect:/query/"+queries.getQ_id();

    }

    @GetMapping("/delete/{q_id}")
    public String deleteUser(@PathVariable("q_id") long q_id, Model model, RedirectAttributes redirectAttributes) {
        Queries query= queriesRepository.findById(q_id)
                .orElseThrow(() -> new IllegalArgumentException("Inavlid"));
        queriesRepository.delete(query);
        redirectAttributes.addFlashAttribute("message", "Deleted Question: "  + query.getQ_id());
        return "redirect:/studentqueries";
    }
}
