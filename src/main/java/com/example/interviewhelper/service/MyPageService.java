package com.example.interviewhelper.service;

import com.example.interviewhelper.dto.mypage.ProfileRequestDto;
import com.example.interviewhelper.dto.mypage.ProfileResponseDto;
import com.example.interviewhelper.dto.coverletter.CoverLetterResponseDto; // 추가 확인
import com.example.interviewhelper.dto.interview.InterviewHistoryDto; // 추가 확인
import com.example.interviewhelper.entity.User;
import com.example.interviewhelper.repository.CoverLetterRepository;
import com.example.interviewhelper.repository.InterviewSessionRepository;
import com.example.interviewhelper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

    private final UserRepository userRepository;
    private final CoverLetterRepository coverLetterRepository; // 추가
    private final InterviewSessionRepository interviewSessionRepository; // 추가

    // 1. 프로필 조회
    public ProfileResponseDto getProfile(Long userId) {
        User user = findUserById(userId);
        return new ProfileResponseDto(user);
    }

    // 2. 프로필 수정/등록 (공통)
    @Transactional
    public void updateProfile(Long userId, ProfileRequestDto requestDto) {
        User user = findUserById(userId);
        user.updateProfile(requestDto.getName(), requestDto.getProfileInfo());
    }

    // 3. 자소서 요약 리스트 조회 (설계서 반영)
    public List<CoverLetterResponseDto> getMyCoverLetters(Long userId) {
        User user = findUserById(userId);
        return coverLetterRepository.findAllByUser(user).stream()
                .map(CoverLetterResponseDto::new)
                .collect(Collectors.toList());
    }

    // 4. 면접 연습 이력 조회 (설계서 반영)
    public List<InterviewHistoryDto> getInterviewHistory(Long userId) {
        User user = findUserById(userId);
        return interviewSessionRepository.findAllByUser(user).stream()
                .map(InterviewHistoryDto::new)
                .collect(Collectors.toList());
    }

    // 5. 회원 탈퇴 (설계서 반영)
    @Transactional
    public void withdrawUser(Long userId) {
        User user = findUserById(userId);
        userRepository.delete(user);
    }

    // 공통 사용 메서드
    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
    }
}