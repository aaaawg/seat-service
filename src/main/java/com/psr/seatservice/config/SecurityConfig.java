package com.psr.seatservice.config;

import com.psr.seatservice.handler.UserLoginFailHandler;
import com.psr.seatservice.service.user.UserDetailService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
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
    public WebSecurityCustomizer customizer() {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toH2Console());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**","/program/**", "/login", "/join/**", "/js/**", "/css/**", "/img/**").permitAll()
                .antMatchers("/business/**").hasRole("BIZ")
                .antMatchers("/myPage/**", "/around/**", "/booking/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                    .formLogin().loginPage("/login")
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
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder())
                .and().build();
    }
}
