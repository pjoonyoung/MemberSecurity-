package com.joonyoung.home.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.joonyoung.home.dto.MemberDto;
import com.joonyoung.home.entity.Member;
import com.joonyoung.home.repository.MemberRepository;

@SpringBootTest
//@Transactional
//@TestPropertySource(locations = "classpath:application-test.properties")
public class MemberServiceTest {
	@Autowired
	MemberService memberService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public Member createMember() {
		
		MemberDto memberDto = new MemberDto();
		memberDto.setMid("tiger222");
		memberDto.setMname("이순신");
		memberDto.setMpw("12345");
		memberDto.setMemail("abcd@abc.com");
		
		
		return Member.createMember(memberDto, passwordEncoder);
		
	}
	
	public Member createMember1() {
		
		MemberDto memberDto = new MemberDto();
		memberDto.setMid("fireDog111");
		memberDto.setMname("강아지");
		memberDto.setMpw("12345");
		memberDto.setMemail("dog@abc.com");
		
		
		return Member.createMember(memberDto, passwordEncoder);
		
	}
	
	@Test
	@DisplayName("회원가입 테스트")
	public void saveMemberTest() {
		Member member1 = createMember();		
		Member savedMember = memberService.saveMember(member1);
		assertEquals(member1.getMid(), savedMember.getMid());
	}
	
	@Test
	@DisplayName("중복 회원가입 테스트")
	public void duplicateMemberTest() {
		
		Member member1 = createMember1();
		Member member2 = createMember1();
		
		memberService.saveMember(member1);//fireDog 가입
		
		Throwable e = assertThrows(IllegalStateException.class, () -> {
		memberService.saveMember(member2);});//테스트 예외처리
		
		System.out.println(e.getMessage());
		assertEquals("이미 가입된 회원입니다!", e.getMessage());
	}
}
