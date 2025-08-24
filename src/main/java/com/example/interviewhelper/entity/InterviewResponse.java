package com.example.interviewhelper.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class InterviewResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 면접 세션에 속한 답변인지 (InterviewSession 1 : N InterviewResponse)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_session_id")
    private InterviewSession interviewSession;

    // 어떤 질문에 대한 답변인지 (PracticeQuestion 1 : N InterviewResponse)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "practice_question_id")
    private PracticeQuestion practiceQuestion;

    private String videoUrl; // 답변 영상이 저장된 경로

    // 시선, 음성 등 분석 데이터 (JSON 형태로 저장)
    @Column(columnDefinition = "TEXT")
    private String gazeAnalysis;
    @Column(columnDefinition = "TEXT")
    private String voiceAnalysis;

    @Builder
    public InterviewResponse(InterviewSession interviewSession, PracticeQuestion practiceQuestion, String videoUrl) {
        this.interviewSession = interviewSession;
        this.practiceQuestion = practiceQuestion;
        this.videoUrl = videoUrl;
    }
}