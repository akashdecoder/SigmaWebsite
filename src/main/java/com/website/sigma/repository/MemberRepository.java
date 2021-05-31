package com.website.sigma.repository;

import com.website.sigma.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select m from Member m where m.email = ?1")
    public Member findByEmail(String email);

    public List<Member> findAllByEmail(String email);

}
