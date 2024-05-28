package com.server.autodevlog.auth.repository;

import com.server.autodevlog.auth.domain.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Long> {
    Optional<Member> findByUserId(String name);
}
