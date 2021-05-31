package com.website.sigma.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id;

    @Column(nullable = false, length = 64)
    private String firstname;

    @Column(nullable = false, length = 64)
    private String lastname;

    @Column(nullable = false, length = 12)
    private String usn;

    @Column(nullable = false, length = 10)
    private String semester;

    @Column(nullable = false, unique = true, length = 64)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(nullable = false, length = 4)
    private int tier;

    @Column(length = 64)
    private String resetpasswordtoken;

    @Column(length = 64, nullable = false)
    private String designation;

    @Column(length = 10, nullable = false)
    private String year;

    @Column(length = 64)
    private String github_url;

    @Column(length = 64)
    private String linkedin_url;

    @Column(length = 1500, nullable = false)
    private String image_url;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "members_roles",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles = new HashSet<>();

    public Member() {
    }

    public Member(String firstname, String lastname, String usn, String semester, String email, String password, int tier, String resetpasswordtoken, String designation, String year, String github_url, String linkedin_url, String image_url) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.usn = usn;
        this.semester = semester;
        this.email = email;
        this.password = password;
        this.tier = tier;
        this.resetpasswordtoken = resetpasswordtoken;
        this.designation = designation;
        this.year = year;
        this.github_url = github_url;
        this.linkedin_url = linkedin_url;
        this.image_url = image_url;
    }

    public Long getMember_id() {
        return member_id;
    }

    public void setMember_id(Long member_id) {
        this.member_id = member_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsn() {
        return usn;
    }

    public void setUsn(String usn) {
        this.usn = usn;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public void addRole(Roles role) {
        this.roles.add(role);
    }

    public boolean hasRole(String roleName) {
        Iterator<Roles> iterator = roles.iterator();
        while(iterator.hasNext()) {
            Roles roles = iterator.next();
            if(roles.getRole_name().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    public String getResetpasswordtoken() {
        return resetpasswordtoken;
    }

    public void setResetpasswordtoken(String resetpasswordtoken) {
        this.resetpasswordtoken = resetpasswordtoken;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGithub_url() {
        return github_url;
    }

    public void setGithub_url(String github_url) {
        this.github_url = github_url;
    }

    public String getLinkedin_url() {
        return linkedin_url;
    }

    public void setLinkedin_url(String linkedin_url) {
        this.linkedin_url = linkedin_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
