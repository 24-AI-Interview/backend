package com.example.interviewhelper.controller;

import com.example.interviewhelper.dto.mypage.ProfileRequestDto;
import com.example.interviewhelper.dto.mypage.ProfileResponseDto;
import com.example.interviewhelper.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    // TODO: 실제로는 JWT 토큰에서 userId를 추출해야 함. 지금은 임시로 파라미터로 받음.
    @GetMapping("/profile/{userId}")
    public ResponseEntity<ProfileResponseDto> getProfile(@PathVariable Long userId) {
        ProfileResponseDto responseDto = myPageService.getProfile(userId);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/profile/{userId}")
    public ResponseEntity<String> updateProfile(@PathVariable Long userId, @RequestBody ProfileRequestDto requestDto) {
        myPageService.updateProfile(userId, requestDto);
        return ResponseEntity.ok("프로필 수정 완료");
    }
}