package com.example.interviewhelper.controller;

import com.example.interviewhelper.dto.interview.*;
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

    // --- [1. 면접 준비 단계] ---

    // 면접 질문 목록 조회 (직무, 유형에 따른 리스트)
    @GetMapping("/questions")
    public ResponseEntity<List<InterviewQuestionDto>> getInterviewQuestions(@RequestParam Long userId) {
        return ResponseEntity.ok(interviewService.startInterview(userId));
    }

    // --- [2. 실제 면접 진행 단계] ---

    // 면접 응답 영상 업로드
    @PostMapping("/record")
    public ResponseEntity<String> uploadAnswerVideo(
            @RequestParam Long sessionId,
            @RequestParam Long questionId,
            @RequestPart("video") MultipartFile videoFile) {
        interviewService.saveAnswerVideo(sessionId, questionId, videoFile);
        return ResponseEntity.ok("영상이 업로드되었습니다.");
    }

    // 시선 분석 결과 전송 (Gaze)
    @PostMapping("/gaze")
    public ResponseEntity<String> submitGazeData(@RequestParam Long sessionId, @RequestBody Object gazeData) {
        interviewService.processGazeData(sessionId, gazeData);
        return ResponseEntity.ok("시선 데이터 전송 완료");
    }

    // 음성 분석 데이터 전송 (Voice)
    @PostMapping("/voice")
    public ResponseEntity<String> submitVoiceData(@RequestParam Long sessionId, @RequestBody Object voiceData) {
        interviewService.processVoiceData(sessionId, voiceData);
        return ResponseEntity.ok("음성 데이터 전송 완료");
    }

    // 표정 분석 데이터 전송 (Expression)
    @PostMapping("/expression")
    public ResponseEntity<String> submitExpressionData(@RequestParam Long sessionId, @RequestBody Object expressionData) {
        interviewService.processExpressionData(sessionId, expressionData);
        return ResponseEntity.ok("표정 데이터 전송 완료");
    }

    // --- [3. 분석 및 리포트 단계] ---

    // 답변 분석 요청 (STT 결과를 NLP로 분석)
    @PostMapping("/analyze")
    public ResponseEntity<String> requestAnalysis(@RequestParam Long sessionId) {
        interviewService.requestNlpAnalysis(sessionId);
        return ResponseEntity.ok("분석 요청 수신");
    }

    // 종합 리포트 생성
    @PostMapping("/report")
    public ResponseEntity<InterviewResultDto> generateReport(@RequestParam Long sessionId) {
        return ResponseEntity.ok(interviewService.generateReport(sessionId));
    }

    // 면접 결과(리포트) 조회
    @GetMapping("/report/{id}")
    public ResponseEntity<InterviewResultDto> getReport(@PathVariable Long id) {
        return ResponseEntity.ok(interviewService.getReportResult(id));
    }

    // 리포트 마이페이지 저장
    @PostMapping("/report/save")
    public ResponseEntity<String> saveReport(@RequestParam Long reportId) {
        interviewService.saveReportToMyPage(reportId);
        return ResponseEntity.ok("마이페이지 저장 완료");
    }
}