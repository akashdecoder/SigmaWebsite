package com.website.sigma.repository;

import com.website.sigma.model.OpenUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpenUserRepository extends JpaRepository<OpenUser, Long> {
    @Query("select t from OpenUser t where t.title = ?1")
    public List<OpenUser> getAllByTitle(String title);
}
