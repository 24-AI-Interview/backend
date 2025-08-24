package com.example.interviewhelper.dto.interview;

import com.example.interviewhelper.entity.InterviewReport;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@NoArgsConstructor
public class InterviewResultDto {
    private Long reportId; // (추가) 리포트 ID
    private Long interviewSessionId;
    private LocalDateTime sessionDate;
    private double overallScore; // 총점
    private String comprehensiveFeedback; // 종합 피드백 텍스트
    private String videoUrl; // 최종 영상 링크
    private Map<String, Double> metricScores; // 항목별 점수 (예: 목소리, 표정, 논리력)

    public InterviewResultDto(InterviewReport report, String videoUrl, Map<String, Double> metricScores) {
        this.reportId = report.getId(); // (추가) 리포트 ID 설정
        this.interviewSessionId = report.getInterviewSession().getId();
        this.sessionDate = report.getInterviewSession().getCreatedAt();
        this.comprehensiveFeedback = report.getComprehensiveFeedback();
        this.videoUrl = videoUrl;
        this.metricScores = metricScores;

        // 항목별 점수의 평균을 계산하여 총점 설정
        if (metricScores != null && !metricScores.isEmpty()) {
            this.overallScore = metricScores.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        }
    }
}