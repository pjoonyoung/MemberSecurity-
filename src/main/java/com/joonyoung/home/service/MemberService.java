package com.joonyoung.home.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joonyoung.home.entity.Member;
import com.joonyoung.home.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberRepository memberRepository;
	
	
	public Member saveMember(Member member) {
		
		validateDuplicateMember(member);
		
		Member returnMember = memberRepository.save(member);
		return returnMember;
	}
	
	private void validateDuplicateMember(Member member) {
		
		Member resultmember = memberRepository.findByMid(member.getMid());
		
		if(resultmember != null) {
			throw new IllegalStateException("이미 가입된 회원입니다.");
		} 
	}
}
