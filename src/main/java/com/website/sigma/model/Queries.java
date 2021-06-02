package com.website.sigma.model;

import javax.persistence.*;

@Entity
@Table(name = "queries")
public class Queries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long q_id;

    @Column(nullable = false, length = 2500)
    private String question;

    @Column(length = 10000)
    private String answer;

    @Column(length = 64)
    private String status;

    public Queries() {
    }

    public Queries(String question, String answer, String status) {
        this.question = question;
        this.answer = answer;
        this.status = status;
    }

    public long getQ_id() {
        return q_id;
    }

    public void setQ_id(long q_id) {
        this.q_id = q_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

