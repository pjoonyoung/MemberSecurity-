package com.joonyoung.home.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.joonyoung.home.service.MemberService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	MemberService memberService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http
        .authorizeRequests()
            .antMatchers("/","/**").access("permitAll")
            .antMatchers("/h2-console/**").permitAll() // 추가
        .and()
            .csrf() // 추가
            .ignoringAntMatchers("/h2-console/**").disable() // 추가
            .httpBasic();
		
		http.formLogin()
			.loginPage("/login")//로그인 페이지 url
			.defaultSuccessUrl("/loginOk")//로그인 성공시 이동할 url
			.usernameParameter("mid")//로그인 시 사용할 파라미터 이름
			.passwordParameter("mpw")
			.failureUrl("/loginFail")//로그인 실패시 이동할 url
			.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))//로그아웃 url
			.logoutUrl("/logoutOk");//로그아웃 성공시 이동할 url
			
	}
	
	public void configure(WebSecurity web)throws Exception{
        web.ignoring().antMatchers("/h2-console/**");
    }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
	}
	
	
	
}
