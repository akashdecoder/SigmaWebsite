package com.website.sigma.repository;

import com.website.sigma.model.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MemberCrudRepository extends CrudRepository<Member, Long> {

    @Query("select m from Member m where m.email = :email")
    public Member getMembersByEmail(@Param("email") String email);
}
