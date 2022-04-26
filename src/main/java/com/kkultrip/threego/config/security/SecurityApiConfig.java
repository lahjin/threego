package com.kkultrip.threego.config.security;

import com.kkultrip.threego.config.security.auth.UserDetailsServiceImpl;
import com.kkultrip.threego.config.security.jwt.AccessDeniedJwt;
import com.kkultrip.threego.config.security.jwt.AuthEntryPointJwt;
import com.kkultrip.threego.config.security.jwt.AuthTokenFilter;
import com.kkultrip.threego.config.security.jwt.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(1)
public class SecurityApiConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final CorsConfig corsConfig;
    private final AuthEntryPointJwt authEntryPointJwt;
    private final AccessDeniedJwt accessDeniedJwt;
    private final JwtUtils jwtutils;

    public SecurityApiConfig(UserDetailsServiceImpl userDetailsService, CorsConfig corsConfig, AuthEntryPointJwt authEntryPointJwt, AccessDeniedJwt accessDeniedJwt, JwtUtils jwtutils) {
        this.userDetailsService = userDetailsService;
        this.corsConfig = corsConfig;
        this.authEntryPointJwt = authEntryPointJwt;
        this.accessDeniedJwt = accessDeniedJwt;
        this.jwtutils = jwtutils;
    }

    @Bean
    public AuthTokenFilter authTokenFilter(){
        return new AuthTokenFilter(jwtutils, userDetailsService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // cors 필터 추가
                .addFilter(corsConfig.corsFilter())
                // csrf 비활성화
                .csrf().disable()

                // 토큰필터
                .addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class)

                //예외처리
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPointJwt)
                .accessDeniedHandler(accessDeniedJwt)
                .and()
                // 세션 사용 안함
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 페이지 접근 권한
                .and()
                .antMatcher("/api/**")
                .authorizeRequests().antMatchers("/api/**").permitAll()
                .anyRequest().authenticated();


    }
}
