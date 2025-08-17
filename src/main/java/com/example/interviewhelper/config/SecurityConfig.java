package com.example.interviewhelper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean // 이 메소드가 반환하는 객체를 Spring이 관리하도록 함
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}