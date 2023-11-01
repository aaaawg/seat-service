package com.psr.seatservice.config;

import com.psr.seatservice.service.user.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailService userService;

    public SecurityConfig(UserDetailService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/business/**").hasRole("BIZ")
                .antMatchers("/myPage/**", "/around/**", "/booking/**").hasRole("USER")
                .antMatchers("/**","/program/**", "/login", "/join/**", "/js/**", "/css/**", "/img/**").permitAll() //접근 전부 허용
                .anyRequest().authenticated() //나머지는 인증 후 접근 가능
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login?error=true")
                    .defaultSuccessUrl("/")
                .and()
                    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true) //로그아웃 이후 세션 전체 삭제 여부
                .and()
                    .csrf().disable();
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class) //AuthenticationManagerBuilder에서 AuthenticationManager 객체를 가져옴
                .userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder())
                .and().build();
    }
}
