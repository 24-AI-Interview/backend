package com.example.interviewhelper.service;

import com.example.interviewhelper.dto.user.UserLoginRequestDto;
import com.example.interviewhelper.dto.user.UserLoginResponseDto;
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
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = new User(requestDto.getEmail(), encodedPassword);
        userRepository.save(user);
    }

    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 설계서 응답 예시 조립 (중첩 클래스 호출 방식)
        UserLoginResponseDto.UserInfo userInfo = UserLoginResponseDto.UserInfo.builder()
                .id(user.getId())
                .name(user.getName() != null ? user.getName() : "홍길동")
                .email(user.getEmail())
                .lastLoginAt("2024-01-10T12:00:00Z")
                .build();

        UserLoginResponseDto.TokenInfo tokenInfo = UserLoginResponseDto.TokenInfo.builder()
                .accessToken("jwt-access-token")
                .refreshToken("jwt-refresh-token")
                .expiresIn(3600)
                .build();

        return UserLoginResponseDto.builder()
                .user(userInfo)
                .tokens(tokenInfo)
                .build();
    }
}