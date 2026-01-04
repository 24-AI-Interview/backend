package com.example.interviewhelper.controller;

import com.example.interviewhelper.dto.coverletter.AiRequestDto;
import com.example.interviewhelper.dto.coverletter.CoverLetterRequestDto;
import com.example.interviewhelper.dto.coverletter.CoverLetterResponseDto;
import com.example.interviewhelper.service.CoverLetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/selfintro")
@RequiredArgsConstructor
public class CoverLetterController {

    private final CoverLetterService coverLetterService;

    // TODO: 실제로는 모든 userId를 JWT 토큰에서 추출해야 함.

    // 1. 자기소개서 업로드 (POST /api/selfintro)
    @PostMapping
    public ResponseEntity<String> createCoverLetter(@RequestBody CoverLetterRequestDto requestDto) {
        // 임시로 userId를 1L로 설정 (나중에 토큰 도입하면 변경)
        coverLetterService.createCoverLetter(1L, requestDto);
        return ResponseEntity.ok("자기소개서 작성 완료");
    }

    // 2. 목록 조회 (GET /api/selfintro)
    @GetMapping
    public ResponseEntity<List<CoverLetterResponseDto>> getMyCoverLetters() {
        List<CoverLetterResponseDto> responseDtos = coverLetterService.getMyCoverLetters(1L);
        return ResponseEntity.ok(responseDtos);
    }

    // 3. 상세 조회 (GET /api/selfintro/{id}) - 설계서에 있던 기능 추가!
    @GetMapping("/{id}")
    public ResponseEntity<CoverLetterResponseDto> getCoverLetter(@PathVariable Long id) {
        return ResponseEntity.ok(coverLetterService.getCoverLetter(id, 1L));
    }

    // 4. 수정 (PUT /api/selfintro/{id})
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCoverLetter(@PathVariable Long id, @RequestBody CoverLetterRequestDto requestDto) {
        coverLetterService.updateCoverLetter(id, 1L, requestDto);
        return ResponseEntity.ok("자기소개서 수정 완료");
    }

    // 5. 삭제 (DELETE /api/selfintro/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCoverLetter(@PathVariable Long id) {
        coverLetterService.deleteCoverLetter(id, 1L);
        return ResponseEntity.ok("자기소개서 삭제 완료");
    }

    // 6. 분량 및 문법 체크 (POST /api/selfintro/analyze)
    @PostMapping("/analyze")
    public ResponseEntity<String> analyze(@RequestBody AiRequestDto requestDto) {
        return ResponseEntity.ok(coverLetterService.analyze(1L, requestDto));
    }

    // 7. 문장 첨삭 요청 (POST /api/selfintro/revise)
    @PostMapping("/revise")
    public ResponseEntity<String> revise(@RequestBody AiRequestDto requestDto) {
        return ResponseEntity.ok(coverLetterService.revise(1L, requestDto));
    }

    // 8. 예상 질문 생성 (POST /api/selfintro/questions)
    @PostMapping("/questions")
    public ResponseEntity<String> generateQuestions(@RequestBody AiRequestDto requestDto) {
        return ResponseEntity.ok(coverLetterService.generateQuestions(1L, requestDto));
    }

    // 9. 자기소개서 요약/피드백 (POST /api/selfintro/summary)
    @PostMapping("/summary")
    public ResponseEntity<String> getSummary(@RequestBody AiRequestDto requestDto) {
        return ResponseEntity.ok(coverLetterService.summary(1L, requestDto));
    }
}