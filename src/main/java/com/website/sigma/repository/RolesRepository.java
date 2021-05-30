package com.website.sigma.repository;

import com.website.sigma.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    @Query("select r from Roles r where r.role_name = ?1")
    public Roles findByRole_name(String name);
}
