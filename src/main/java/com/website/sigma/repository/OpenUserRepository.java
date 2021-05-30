package com.website.sigma.repository;

import com.website.sigma.model.OpenUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpenUserRepository extends JpaRepository<OpenUser, Long> {
}
