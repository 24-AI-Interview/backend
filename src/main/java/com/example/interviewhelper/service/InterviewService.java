package com.example.interviewhelper.service;

import com.example.interviewhelper.dto.interview.InterviewQuestionDto;
import com.example.interviewhelper.dto.interview.InterviewResultDto;
import com.example.interviewhelper.entity.*;
import com.example.interviewhelper.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value; // (추가) @Value 임포트
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InterviewService {

    private final UserRepository userRepository;
    private final InterviewSessionRepository interviewSessionRepository;
    private final InterviewResponseRepository interviewResponseRepository;
    private final InterviewReportRepository interviewReportRepository;
    private final PracticeQuestionRepository practiceQuestionRepository;

    // (수정) 설정 파일(application.yml)에서 파일 저장 경로를 주입받음
    @Value("${file.upload-dir}")
    private String uploadDir;

    // 1. 면접 시작
    @Transactional
    public List<InterviewQuestionDto> startInterview(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        InterviewSession session = new InterviewSession(user);
        interviewSessionRepository.save(session);

        return practiceQuestionRepository.findAll().stream()
                .map(InterviewQuestionDto::new)
                .collect(Collectors.toList());
    }

    // 2. 답변 영상 저장
    @Transactional
    public void saveAnswerVideo(Long interviewSessionId, Long questionId, MultipartFile videoFile) {
        InterviewSession session = interviewSessionRepository.findById(interviewSessionId)
                .orElseThrow(() -> new IllegalArgumentException("면접 세션을 찾을 수 없습니다."));
        PracticeQuestion question = practiceQuestionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("질문을 찾을 수 없습니다."));

        // (수정) 하드코딩된 경로 대신 주입받은 경로 사용
        String uniqueFileName = UUID.randomUUID().toString() + "_" + videoFile.getOriginalFilename();
        File dest = new File(uploadDir + uniqueFileName);
        try {
            videoFile.transferTo(dest);
        } catch (IOException e) {
            throw new RuntimeException("영상 파일 저장에 실패했습니다.", e);
        }

        InterviewResponse response = InterviewResponse.builder()
                .interviewSession(session)
                .practiceQuestion(question)
                .videoUrl(dest.getPath())
                .build();

        interviewResponseRepository.save(response);
    }

    // 3. 실시간 분석 데이터 처리
    public void processGazeData(Long interviewSessionId, Object gazeData) {
        System.out.println("세션 ID " + interviewSessionId + "의 시선 데이터 수신: " + gazeData.toString());
    }

    // 4. 리포트 생성
    @Transactional
    public InterviewResultDto generateReport(Long interviewSessionId) {
        InterviewSession session = interviewSessionRepository.findById(interviewSessionId)
                .orElseThrow(() -> new IllegalArgumentException("면접 세션을 찾을 수 없습니다."));

        String aiFeedback = "종합적인 AI 분석 결과입니다. (현재는 임시 텍스트)";

        InterviewReport report = InterviewReport.builder()
                .interviewSession(session)
                .comprehensiveFeedback(aiFeedback)
                .isSaved(false)
                .build();
        interviewReportRepository.save(report);

        String videoUrl = "https://your-video-url.com/video.mp4";
        Map<String, Double> metricScores = new HashMap<>();
        metricScores.put("음성 점수", 90.0);
        metricScores.put("표정 점수", 85.0);

        return new InterviewResultDto(report, videoUrl, metricScores);
    }

    // 5. 리포트 조회 (수정: 읽기 전용 트랜잭션 옵션 추가)
    @Transactional(readOnly = true)
    public InterviewResultDto getReportResult(Long reportId) {
        InterviewReport report = interviewReportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("리포트를 찾을 수 없습니다."));

        String videoUrl = "https://your-video-url.com/video.mp4";
        Map<String, Double> metricScores = new HashMap<>();
        metricScores.put("음성 점수", 90.0);
        metricScores.put("표정 점수", 85.0);

        return new InterviewResultDto(report, videoUrl, metricScores);
    }
}