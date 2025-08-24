package com.example.interviewhelper.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class InterviewSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이 세션을 진행한 사용자 (User 1 : N InterviewSession)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // InterviewReport와 1:1 관계 설정
    @OneToOne(mappedBy = "interviewSession", cascade = CascadeType.ALL)
    private InterviewReport interviewReport;

    // 생성 시 자동으로 현재 시간 기록
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public InterviewSession(User user) {
        this.user = user;
    }
}