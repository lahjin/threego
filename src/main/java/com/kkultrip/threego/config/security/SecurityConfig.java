package com.kkultrip.threego.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(2)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(
                        "/h2-console/**",
                        "/favicon.ico",
                        "/error",
                        "/css/**",
                        "/js/**"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // csrf 비활성화
                .csrf().disable()

                // /** (모든경로) 접근 설정 /login 만 허용하고 나머지는 인증없이는 접근 불가
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()

                // 기본 Form 형식의 로그인 로직 사용 /login 으로 POST 처리 후 성공 시 "/" 으로 이동
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")

                // 로그아웃시 세션 삭제 및 쿠키 삭제
                .and()
                .logout()
                .invalidateHttpSession(true).deleteCookies("JSESSIONID")

                // h2-console frame 관련 오류 방지
                .and()
                .headers()
                .frameOptions()
                .sameOrigin();
    }
}
