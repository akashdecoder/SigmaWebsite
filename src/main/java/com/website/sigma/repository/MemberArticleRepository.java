package com.website.sigma.repository;

import com.website.sigma.model.MemberArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberArticleRepository extends JpaRepository<MemberArticle, Long> {
}
