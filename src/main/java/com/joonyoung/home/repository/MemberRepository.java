package com.joonyoung.home.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joonyoung.home.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
	public Member findByMid(String mid);
}
