package com.website.sigma.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "memberarticle")
@NoArgsConstructor
@AllArgsConstructor
public class MemberArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter long openuser_id;

    @Column(nullable = false, length = 64)
    private @Getter @Setter String firstname;

    @Column(nullable = false, length = 64)
    private @Getter @Setter String lastname;

    @Column(nullable = false, length = 10)
    private @Getter @Setter String usn;

    @Column(nullable = false, length = 10)
    private @Getter @Setter String semester;

    @Column(nullable = false, length = 64)
    private @Getter @Setter String title;

    @Column(nullable = false, length = 510)
    private @Getter @Setter String introduction;

    @Column(nullable = false, length = 1510)
    private @Getter @Setter String body1;

    @Column(length = 1510)
    private @Getter @Setter String body2;

    @Column(nullable = false, length = 510)
    private @Getter @Setter String conclusion;

}
