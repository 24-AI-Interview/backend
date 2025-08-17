package com.example.interviewhelper.service;

import com.example.interviewhelper.dto.mypage.ProfileRequestDto;
import com.example.interviewhelper.dto.mypage.ProfileResponseDto;
import com.example.interviewhelper.entity.User;
import com.example.interviewhelper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;

    public ProfileResponseDto getProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
        return new ProfileResponseDto(user);
    }

    @Transactional
    public void updateProfile(Long userId, ProfileRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
        user.updateProfile(requestDto.getName(), requestDto.getProfileInfo());
    }
}