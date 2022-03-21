package com.website.sigma.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter long n_id;

    @Column(nullable = false, length = 64)
    private @Getter @Setter String name;

    @Column(nullable = false, length = 2510)
    private @Getter @Setter String notifications;

}
