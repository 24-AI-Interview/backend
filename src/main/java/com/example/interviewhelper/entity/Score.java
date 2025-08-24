package com.example.interviewhelper.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 항목의 점수인지 (예: "목소리 점수", "논리력 점수")
    private String category;

    @Column(nullable = false)
    private Double scoreValue; // 점수 값 (예: 85.5)

    // 어떤 면접 세션의 점수인지 (InterviewSession 1 : N Score)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_session_id")
    private InterviewSession interviewSession;

    // 어떤 인성 검사 결과의 점수인지 (TestResult 1 : N Score)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_result_id")
    private TestResult testResult;
}