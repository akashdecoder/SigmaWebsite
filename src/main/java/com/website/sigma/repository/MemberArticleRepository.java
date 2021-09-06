package com.website.sigma.repository;

import com.website.sigma.model.MemberArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberArticleRepository extends JpaRepository<MemberArticle, Long> {

    @Query("select t from MemberArticle t where t.usn = ?1")
    public List<MemberArticle> findAllByUsn(String usn);

}
