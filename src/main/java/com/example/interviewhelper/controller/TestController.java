package com.example.interviewhelper.controller;

import com.example.interviewhelper.dto.test.*; // DTO들 포함
import com.example.interviewhelper.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    // 1. 검사 목록 조회 (GET /api/test/types)
    @GetMapping("/types")
    public ResponseEntity<List<String>> getTestTypes() {
        return ResponseEntity.ok(testService.getAllTestTypes());
    }

    // 2. 권한 허용 여부 저장 (POST /api/test/permissions)
    @PostMapping("/permissions")
    public ResponseEntity<String> savePermissions(@RequestBody PermissionRequestDto dto) {
        testService.savePermissions(dto);
        return ResponseEntity.ok("권한 정보 저장 완료");
    }

    // 3. 검사 시작 요청 (POST /api/test/start)
    @PostMapping("/start")
    public ResponseEntity<String> startTest(@RequestBody TestStartRequestDto requestDto) {
        Long sessionId = testService.startTest(requestDto);
        return ResponseEntity.ok("검사 시작. 세션 ID: " + sessionId);
    }

    // 4. 문항 응답 제출 (POST /api/test/submit)
    @PostMapping("/submit")
    public ResponseEntity<String> submitAnswer(@RequestBody TestSubmissionDto submissionDto) {
        testService.submitAnswer(submissionDto);
        return ResponseEntity.ok("응답 저장 완료");
    }

    // 5. 표정 분석 데이터 전송 (POST /api/test/face-emotion)
    @PostMapping("/face-emotion")
    public ResponseEntity<String> sendFaceEmotion(
            @RequestParam Long sessionId,
            @RequestPart("file") MultipartFile file) {
        testService.analyzeFaceEmotion(sessionId, file);
        return ResponseEntity.ok("표정 데이터 분석 완료");
    }

    // 6. 검사 종료 및 결과 생성 (POST /api/test/complete)
    @PostMapping("/complete")
    public ResponseEntity<TestResultDto> completeTest(@RequestParam Long sessionId) {
        return ResponseEntity.ok(testService.completeTest(sessionId));
    }

    // 7. 검사 결과 조회 (GET /api/test/result/{testId})
    @GetMapping("/result/{testId}")
    public ResponseEntity<TestResultDto> getTestResult(@PathVariable Long testId) {
        return ResponseEntity.ok(testService.getTestResult(testId));
    }

    // 8. 검사 결과 저장 (POST /api/test/result/save)
    @PostMapping("/result/save")
    public ResponseEntity<String> saveResultRecord(@RequestParam Long resultId) {
        testService.saveResultToMyPage(resultId);
        return ResponseEntity.ok("마이페이지 저장 완료");
    }

    // 9. 검사 리포트 PDF 다운로드 (GET /api/test/result/{testId}/pdf)
    @GetMapping("/result/{testId}/pdf")
    public ResponseEntity<String> downloadPdf(@PathVariable Long testId) {
        String pdfUrl = testService.getPdfUrl(testId);
        return ResponseEntity.ok("PDF 다운로드 링크: " + pdfUrl);
    }
}