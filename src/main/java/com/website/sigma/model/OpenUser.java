package com.website.sigma.model;

import javax.persistence.*;

@Entity
@Table(name = "openuser")
public class OpenUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long openuser_id;

    @Column(nullable = false, length = 64)
    private String firstname;

    @Column(nullable = false, length = 64)
    private String lastname;

    @Column(nullable = false, length = 10)
    private String usn;

    @Column(nullable = false, length = 10)
    private String semester;

    @Column(nullable = false, length = 64)
    private String title;

    @Column(nullable = false, length = 510)
    private String introduction;

    @Column(nullable = false, length = 1510)
    private String body1;

    @Column(length = 1510)
    private String body2;

    @Column(nullable = false, length = 510)
    private String conclusion;


    public OpenUser() {
    }

    public OpenUser(String firstname, String lastname, String usn, String semester, String title, String introduction, String body1, String body2, String conclusion) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.usn = usn;
        this.semester = semester;
        this.title = title;
        this.introduction = introduction;
        this.body1 = body1;
        this.body2 = body2;
        this.conclusion = conclusion;
    }

    public long getOpenuser_id() {
        return openuser_id;
    }

    public void setOpenuser_id(long openuser_id) {
        this.openuser_id = openuser_id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getBody1() {
        return body1;
    }

    public void setBody1(String body1) {
        this.body1 = body1;
    }

    public String getBody2() {
        return body2;
    }

    public void setBody2(String body2) {
        this.body2 = body2;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }
}
