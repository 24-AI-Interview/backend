package com.example.interviewhelper.controller;

import com.example.interviewhelper.dto.mypage.ProfileRequestDto;
import com.example.interviewhelper.dto.mypage.ProfileResponseDto;
import com.example.interviewhelper.dto.coverletter.CoverLetterResponseDto;
import com.example.interviewhelper.dto.interview.InterviewHistoryDto;
import com.example.interviewhelper.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    // --- 1. 사용자 이력 정보 조회 (설계서: GET /api/mypage/profile) ---
    @GetMapping("/mypage/profile")
    public ResponseEntity<ProfileResponseDto> getProfile(@RequestParam Long userId) {
        return ResponseEntity.ok(myPageService.getProfile(userId));
    }

    // --- 2. 사용자 이력 정보 입력 (설계서: POST /api/mypage/profile) ---
    @PostMapping("/mypage/profile")
    public ResponseEntity<String> createProfile(@RequestParam Long userId, @RequestBody ProfileRequestDto requestDto) {
        myPageService.updateProfile(userId, requestDto);
        return ResponseEntity.ok("이력 정보 등록 완료");
    }

    // --- 3. 자기소개서 요약 리스트 조회 (설계서: GET /api/mypage/selfintro) ---
    @GetMapping("/mypage/selfintro")
    public ResponseEntity<List<CoverLetterResponseDto>> getMySelfIntros(@RequestParam Long userId) {
        return ResponseEntity.ok(myPageService.getMyCoverLetters(userId));
    }

    // --- 4. 면접 이력 리스트 조회 (설계서: GET /api/interviews?userId=xxx) ---
    @GetMapping("/interviews")
    public ResponseEntity<List<InterviewHistoryDto>> getInterviewHistory(@RequestParam Long userId) {
        return ResponseEntity.ok(myPageService.getInterviewHistory(userId));
    }

    // --- 5. 계정 정보 수정 (설계서: PATCH /api/user) ---
    @PatchMapping("/user")
    public ResponseEntity<String> updateAccount(@RequestParam Long userId, @RequestBody ProfileRequestDto requestDto) {
        myPageService.updateProfile(userId, requestDto);
        return ResponseEntity.ok("계정 정보 수정 완료");
    }

    // --- 6. 회원 탈퇴 (설계서: DELETE /api/user) ---
    @DeleteMapping("/user")
    public ResponseEntity<String> withdraw(@RequestParam Long userId) {
        myPageService.withdrawUser(userId);
        return ResponseEntity.ok("회원 탈퇴 처리 완료");
    }
}