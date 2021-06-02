package com.website.sigma.repository;

import com.website.sigma.model.Queries;
import com.website.sigma.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueriesRepository extends JpaRepository<Queries, Long> {
    @Query("select q from Queries q where q.status = ?1")
    public List<Queries> findAllByStatus(String status);
}
