package com.website.sigma.model;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long n_id;

    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false, length = 2510)
    private String notifications;

    public Messages() {
    }


    public Messages(String name, String notifications) {
        this.name = name;
        this.notifications = notifications;
    }

    public long getN_id() {
        return n_id;
    }

    public void setN_id(long n_id) {
        this.n_id = n_id;
    }

    public String getNotifications() {
        return notifications;
    }

    public void setNotifications(String notifications) {
        this.notifications = notifications;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
