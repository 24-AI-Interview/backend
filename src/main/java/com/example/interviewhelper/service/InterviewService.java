package com.example.interviewhelper.service;

import com.example.interviewhelper.dto.interview.*;
import com.example.interviewhelper.entity.*;
import com.example.interviewhelper.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterviewService {

    private final UserRepository userRepository;
    private final InterviewSessionRepository interviewSessionRepository;
    private final InterviewResponseRepository interviewResponseRepository;
    private final InterviewReportRepository interviewReportRepository;
    private final PracticeQuestionRepository practiceQuestionRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // 1. 면접 시작 (질문 목록 조회)
    @Transactional
    public List<InterviewQuestionDto> startInterview(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        InterviewSession session = new InterviewSession(user);
        InterviewSession savedSession = interviewSessionRepository.save(session);

        return practiceQuestionRepository.findAll().stream()
                .map(q -> new InterviewQuestionDto(savedSession.getId(), q))
                .collect(Collectors.toList());
    }

    // 2. 답변 영상 저장
    @Transactional
    public void saveAnswerVideo(Long sessionId, Long questionId, MultipartFile videoFile) {
        InterviewSession session = interviewSessionRepository.findById(sessionId).orElseThrow();
        PracticeQuestion question = practiceQuestionRepository.findById(questionId).orElseThrow();

        String fileName = "video_" + sessionId + "_" + UUID.randomUUID() + ".mp4";
        File dest = new File(uploadDir, fileName);
        try {
            videoFile.transferTo(dest);
        } catch (IOException e) {
            throw new RuntimeException("영상 저장 실패", e);
        }

        InterviewResponse response = InterviewResponse.builder()
                .interviewSession(session)
                .practiceQuestion(question)
                .videoUrl(dest.getPath())
                .build();
        interviewResponseRepository.save(response);
    }

    // 3. 실시간 분석 데이터 처리 (에러 해결 포인트!)
    public void processGazeData(Long sessionId, Object data) {
        System.out.println("세션 " + sessionId + " 시선 데이터 처리 중...");
    }

    public void processVoiceData(Long sessionId, Object data) {
        System.out.println("세션 " + sessionId + " 음성 데이터 처리 중...");
    }

    public void processExpressionData(Long sessionId, Object data) {
        System.out.println("세션 " + sessionId + " 표정 데이터 처리 중...");
    }

    // 4. 분석 요청 (에러 해결 포인트!)
    public void requestNlpAnalysis(Long sessionId) {
        System.out.println("세션 " + sessionId + " NLP 분석 시작...");
    }

    // 5. 리포트 생성 및 저장
    @Transactional
    public InterviewResultDto generateReport(Long sessionId) {
        InterviewSession session = interviewSessionRepository.findById(sessionId).orElseThrow();
        InterviewReport report = InterviewReport.builder()
                .interviewSession(session)
                .comprehensiveFeedback("분석 완료 피드백")
                .isSaved(false)
                .build();
        interviewReportRepository.save(report);

        Map<String, Double> scores = new HashMap<>();
        scores.put("논리력", 85.0);
        return new InterviewResultDto(report, "video_url", scores);
    }

    public InterviewResultDto getReportResult(Long reportId) {
        InterviewReport report = interviewReportRepository.findById(reportId).orElseThrow();
        return new InterviewResultDto(report, "video_url", new HashMap<>());
    }

    @Transactional
    public void saveReportToMyPage(Long reportId) {
        System.out.println("리포트 " + reportId + " 마이페이지 저장 완료");
    }
}