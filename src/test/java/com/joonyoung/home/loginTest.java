package com.joonyoung.home;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.joonyoung.home.dto.MemberDto;
import com.joonyoung.home.entity.Member;
import com.joonyoung.home.service.MemberService;

@SpringBootTest
@AutoConfigureMockMvc
//@TestPropertySource(locations = "classpath:application-test.properties")
public class loginTest {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public Member createMember(String mid, String mpw) {
		
		MemberDto memberDto = new MemberDto();
		memberDto.setMid(mid);
		memberDto.setMname("홍길");
		memberDto.setMpw(mpw);
		memberDto.setMemail("hong@abc.com");
		
		Member member = Member.createMember(memberDto, passwordEncoder);
		
		Member svaedMember = memberService.saveMember(member);
		
		return svaedMember;
		
	}
	
	@Test
	@DisplayName("로그인 성공 테스트")
	public void loginSuccessTest() throws Exception {
		
		String mid = "black";
		String mpw = "1234";
		
		createMember(mid, mpw);//black/1234 계정으로 회원 가입
		
		mockMvc.perform(
				formLogin().userParameter("mid")
				.loginProcessingUrl("/login")
				.user(mid).password(mpw)).andExpect(SecurityMockMvcResultMatchers.authenticated());
		
	}
}
