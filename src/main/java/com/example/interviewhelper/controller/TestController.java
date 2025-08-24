package com.example.interviewhelper.controller;

import com.example.interviewhelper.dto.test.TestResultDto;
import com.example.interviewhelper.dto.test.TestStartRequestDto;
import com.example.interviewhelper.dto.test.TestSubmissionDto;
import com.example.interviewhelper.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    // TODO: 모든 userId는 JWT 토큰에서 추출하도록 최종 수정 필요

    // 1. 검사 시작 요청
    @PostMapping("/start")
    public ResponseEntity<String> startTest(@RequestBody TestStartRequestDto requestDto) {
        // requestDto 안에는 userId, testType 같은 정보가 포함됩니다.
        Long testSessionId = testService.startTest(requestDto);
        return ResponseEntity.ok("검사를 시작합니다. 세션 ID: " + testSessionId);
    }

    // 2. 문항 응답 제출
    @PostMapping("/submit")
    public ResponseEntity<String> submitAnswer(@RequestBody TestSubmissionDto submissionDto) {
        // submissionDto 안에는 testSessionId, questionId, answer 같은 정보가 포함됩니다.
        testService.submitAnswer(submissionDto);
        return ResponseEntity.ok("답변이 제출되었습니다.");
    }

    // 3. 검사 종료 및 결과 생성
    @PostMapping("/complete")
    public ResponseEntity<TestResultDto> completeTest(@RequestParam Long testSessionId) {
        TestResultDto result = testService.completeTest(testSessionId);
        return ResponseEntity.ok(result);
    }

    // 4. 검사 결과 조회
    @GetMapping("/result/{resultId}")
    public ResponseEntity<TestResultDto> getTestResult(@PathVariable Long resultId) {
        TestResultDto result = testService.getTestResult(resultId);
        return ResponseEntity.ok(result);
    }
}