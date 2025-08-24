package com.example.interviewhelper.controller;


import com.example.interviewhelper.dto.interview.InterviewQuestionDto;
import com.example.interviewhelper.dto.interview.InterviewResultDto;
import com.example.interviewhelper.service.InterviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/interview")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;

    // TODO: 모든 userId는 JWT 토큰에서 추출하도록 최종 수정 필요

    // 1. 면접 질문 목록 조회 (면접 시작)
    @GetMapping("/questions")
    public ResponseEntity<List<InterviewQuestionDto>> getInterviewQuestions(@RequestParam Long userId) {
        List<InterviewQuestionDto> questions = interviewService.startInterview(userId);
        return ResponseEntity.ok(questions);
    }

    // 2. 면접 응답 영상 업로드
    @PostMapping("/record")
    public ResponseEntity<String> uploadAnswerVideo(
            @RequestParam Long interviewSessionId,
            @RequestParam Long questionId,
            @RequestPart("video") MultipartFile videoFile) {
        interviewService.saveAnswerVideo(interviewSessionId, questionId, videoFile);
        return ResponseEntity.ok("답변 영상이 업로드되었습니다.");
    }

    // 3. 실시간 분석 데이터 전송 (시선, 음성, 표정 등)
    @PostMapping("/gaze")
    public ResponseEntity<String> submitGazeData(@RequestParam Long interviewSessionId, @RequestBody Object gazeData) {
        interviewService.processGazeData(interviewSessionId, gazeData);
        return ResponseEntity.ok("시선 데이터가 전송되었습니다.");
    }

    // 4. 종합 리포트 생성 요청 (수정: 반환 타입을 InterviewResultDto로 변경)
    @PostMapping("/report")
    public ResponseEntity<InterviewResultDto> generateReport(@RequestParam Long interviewSessionId) {
        InterviewResultDto reportResult = interviewService.generateReport(interviewSessionId);
        return ResponseEntity.ok(reportResult);
    }

    // 5. 면접 결과(리포트) 조회 API
    @GetMapping("/report/{reportId}")
    public ResponseEntity<InterviewResultDto> getReport(@PathVariable Long reportId) {
        InterviewResultDto resultDto = interviewService.getReportResult(reportId);
        return ResponseEntity.ok(resultDto);
    }
}