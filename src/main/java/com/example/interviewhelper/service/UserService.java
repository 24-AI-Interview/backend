package com.example.interviewhelper.service;

import com.example.interviewhelper.dto.user.UserLoginRequestDto;
import com.example.interviewhelper.dto.user.UserSignupRequestDto;
import com.example.interviewhelper.entity.User;
import com.example.interviewhelper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(UserSignupRequestDto requestDto) {
        // 이메일 중복 확인
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(requestDto.getEmail(), encodedPassword);
        userRepository.save(user);
    }

    public String login(UserLoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));

        // 비밀번호 확인
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // TODO: 로그인 성공 시 JWT 토큰 생성 로직 (지금은 임시로 "로그인 성공" 문자열 반환)
        return "로그인 성공! "; // JWT 토큰 위치
    }
}