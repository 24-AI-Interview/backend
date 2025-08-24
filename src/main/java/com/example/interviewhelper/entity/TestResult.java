package com.example.interviewhelper.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 검사 세션의 결과인지 (1:1 관계)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_session_id", unique = true)
    private PersonalityTestSession testSession;

    @Column(columnDefinition = "TEXT")
    private String resultData; // JSON 형태로 저장된 검사 결과 데이터

    private String pdfUrl; // PDF 리포트 파일 경로

    @Builder
    public TestResult(PersonalityTestSession testSession, String resultData, String pdfUrl) {
        this.testSession = testSession;
        this.resultData = resultData;
        this.pdfUrl = pdfUrl; // <-- 이 부분을 추가했습니다!
    }
}