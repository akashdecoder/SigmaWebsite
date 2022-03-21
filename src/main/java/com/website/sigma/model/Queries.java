package com.website.sigma.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "queries")
@NoArgsConstructor
@AllArgsConstructor
public class Queries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter long q_id;

    @Column(nullable = false, length = 2500)
    private @Getter @Setter String question;

    @Column(length = 10000)
    private @Getter @Setter String answer;

    @Column(length = 64)
    private @Getter @Setter String status;
}

