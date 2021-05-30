package com.website.sigma.model;


import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;

    @Column
    String role_name;

    public Roles() {
    }

    public Roles(Long role_id, String role_name) {
        this.role_id = role_id;
        this.role_name = role_name;
    }

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
}
