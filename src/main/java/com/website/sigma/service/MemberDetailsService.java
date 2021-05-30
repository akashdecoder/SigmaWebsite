package com.website.sigma.service;

import com.website.sigma.model.Member;
import com.website.sigma.repository.MemberRepository;
import com.website.sigma.security.MemberDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MemberDetailsService implements UserDetailsService {
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);
        if(member == null) {
            throw new UsernameNotFoundException("User not Found");
        }
        return new MemberDetails(member);
    }
}