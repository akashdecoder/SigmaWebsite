package com.website.sigma.controller;

import com.website.sigma.model.Member;
import com.website.sigma.model.Roles;
import com.website.sigma.repository.MemberRepository;
import com.website.sigma.repository.RolesRepository;
import com.website.sigma.security.MemberDetails;
import com.website.sigma.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CrudController {

    @Autowired
    private ValidationService validationService;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private MemberRepository memberRepository;

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
                return"redirect:/account";
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
}
