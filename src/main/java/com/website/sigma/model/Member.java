package com.website.sigma.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@Table(name = "members")
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter @Setter Long member_id;

    @Column(nullable = false, length = 64)
    private @Getter @Setter String firstname;

    @Column(nullable = false, length = 64)
    private @Getter @Setter String lastname;

    @Column(nullable = false, length = 12)
    private @Getter @Setter String usn;

    @Column(nullable = false, length = 10)
    private @Getter @Setter String semester;

    @Column(nullable = false, unique = true, length = 64)
    private @Getter @Setter String email;

    @Column(nullable = false, length = 64)
    private @Getter @Setter String password;

    @Column(nullable = false, length = 4)
    private @Getter @Setter int tier;

    @Column(length = 64)
    private @Getter @Setter String resetpasswordtoken;

    @Column(length = 64, nullable = false)
    private @Getter @Setter String designation;

    @Column(length = 10, nullable = false)
    private @Getter @Setter String year;

    @Column(length = 64)
    private @Getter @Setter String github_url;

    @Column(length = 64)
    private @Getter @Setter String linkedin_url;

    @Column(length = 1500, nullable = false)
    private @Getter @Setter String image_url;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "members_roles",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private @Getter @Setter Set<Roles> roles = new HashSet<>();

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
}
