package com.example.interviewhelper.controller;

import com.example.interviewhelper.dto.prep.PrepQuestionDto;
import com.example.interviewhelper.service.PrepService; // 서비스 연결 준비
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prep")
@RequiredArgsConstructor
public class PrepController {

    private final PrepService prepService; // 빨간줄 뜨면 서비스 파일 만들면 돼!

    // 1. 가이드 영상 리스트 조회 (GET /api/prep/videos)
    @GetMapping("/videos")
    public ResponseEntity<String> getVideos() {
        return ResponseEntity.ok("면접 준비용 영상 리스트 조회");
    }

    // 2. 질문 예시 조회 (GET /api/prep/questions)
    @GetMapping("/questions")
    public ResponseEntity<List<PrepQuestionDto>> getQuestions() {
        return ResponseEntity.ok(prepService.getSampleQuestions());
    }

    // 3. 즐겨찾기 추가 (POST /api/prep/bookmark)
    @PostMapping("/bookmark")
    public ResponseEntity<String> addBookmark() {
        return ResponseEntity.ok("즐겨찾기 등록");
    }

    // 4. 즐겨찾기 삭제 (DELETE /api/prep/bookmark/{id})
    @DeleteMapping("/bookmark/{id}")
    public ResponseEntity<String> deleteBookmark(@PathVariable Long id) {
        return ResponseEntity.ok("즐겨찾기 해제");
    }

    // 5. 즐겨찾기 목록 조회 (GET /api/prep/bookmark?userId=1)
    @GetMapping("/bookmark")
    public ResponseEntity<List<PrepQuestionDto>> getBookmarks(@RequestParam Long userId) {
        return ResponseEntity.ok(prepService.getBookmarkList(userId));
    }
}