package com.website.sigma.service;

import com.website.sigma.model.Member;
import com.website.sigma.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServices {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ValidationService validationService;

    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if(member != null) {
            member.setResetpasswordtoken(token);
            memberRepository.save(member);
        } else {
            throw new UsernameNotFoundException("Could not find any user with email " + email);
        }
    }

    public Member getByResetPasswordToken(String token) {
        return memberRepository.findByResetpasswordtoken(token);
    }

    public void updatePassword(Member member, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        member.setPassword(encodedPassword);

        member.setResetpasswordtoken(null);
        memberRepository.save(member);
    }
}
