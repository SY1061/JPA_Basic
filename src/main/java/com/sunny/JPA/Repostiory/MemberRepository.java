package com.sunny.JPA.Repostiory;

import com.sunny.JPA.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
