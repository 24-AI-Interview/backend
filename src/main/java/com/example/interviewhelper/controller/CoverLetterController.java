package com.example.interviewhelper.controller;

import com.example.interviewhelper.dto.coverletter.CoverLetterRequestDto;
import com.example.interviewhelper.dto.coverletter.CoverLetterResponseDto;
import com.example.interviewhelper.service.CoverLetterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/selfintros")
@RequiredArgsConstructor
public class CoverLetterController {

    private final CoverLetterService coverLetterService;

    // TODO: 실제로는 모든 userId를 JWT 토큰에서 추출해야 함.

    @PostMapping("/{userId}")
    public ResponseEntity<String> createCoverLetter(@PathVariable Long userId, @RequestBody CoverLetterRequestDto requestDto) {
        coverLetterService.createCoverLetter(userId, requestDto);
        return ResponseEntity.ok("자기소개서 작성");
    }

    @PostMapping("/analyze")
    public ResponseEntity<String> analyzeCoverLetter(
            @RequestParam("userId") Long userId,
            @RequestParam("industry") String industry,
            @RequestParam("job") String job,
            @RequestParam(value = "text", required = false) String text,
            @RequestParam(value = "savedId", required = false) Long savedId,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        String result = coverLetterService.analyze(userId, industry, job, text, savedId, file);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CoverLetterResponseDto>> getMyCoverLetters(@PathVariable Long userId) {
        List<CoverLetterResponseDto> responseDtos = coverLetterService.getMyCoverLetters(userId);
        return ResponseEntity.ok(responseDtos);
    }

    @PatchMapping("/{coverLetterId}/user/{userId}")
    public ResponseEntity<String> updateCoverLetter(@PathVariable Long coverLetterId,
                                                    @PathVariable Long userId,
                                                    @RequestBody CoverLetterRequestDto requestDto) {
        coverLetterService.updateCoverLetter(coverLetterId, userId, requestDto);
        return ResponseEntity.ok("자기소개서 수정 완료");
    }

    @DeleteMapping("/{coverLetterId}/user/{userId}")
    public ResponseEntity<String> deleteCoverLetter(@PathVariable Long coverLetterId,
                                                    @PathVariable Long userId) {
        coverLetterService.deleteCoverLetter(coverLetterId, userId);
        return ResponseEntity.ok("자기소개서 삭제");
    }
}