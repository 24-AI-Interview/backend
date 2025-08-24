package com.example.interviewhelper.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime; // LocalDateTime 임포트 추가

@Entity
@Getter
@NoArgsConstructor
public class InterviewReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_session_id", unique = true)
    private InterviewSession interviewSession;

    @Column(columnDefinition = "TEXT")
    private String comprehensiveFeedback;

    private boolean isSaved;

    // (수정) 생성 시간 필드 추가
    private LocalDateTime createdAt;

    // (수정) 엔티티가 저장되기 직전에 현재 시간을 자동으로 설정
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @Builder
    public InterviewReport(InterviewSession interviewSession, String comprehensiveFeedback, boolean isSaved) {
        this.interviewSession = interviewSession;
        this.comprehensiveFeedback = comprehensiveFeedback;
        this.isSaved = isSaved;
    }
}