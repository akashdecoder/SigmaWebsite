package com.website.sigma.security;

import com.website.sigma.model.Member;
import com.website.sigma.model.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class MemberDetails implements UserDetails {

    private Member member;

    public MemberDetails(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Roles> roles = member.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(Roles role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole_name()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setFirstName(String firstname) {
        this.member.setFirstname(firstname);
    }

    public void setLastName(String lastname) {
        this.member.setLastname(lastname);
    }

    public boolean hasRole(String roleName) {
        return member.hasRole(roleName);
    }

    public String getFullName() {
        return member.getFirstname() + " " + member.getLastname();
    }
}

