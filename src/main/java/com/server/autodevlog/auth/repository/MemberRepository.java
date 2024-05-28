package com.server.autodevlog.auth.repository;

import com.server.autodevlog.auth.domain.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
    Member findByUserId(String name);
}
