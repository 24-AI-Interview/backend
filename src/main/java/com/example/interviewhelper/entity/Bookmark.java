package com.example.interviewhelper.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private PracticeQuestion practiceQuestion; // 기존 면접 질문 엔티티랑 연결

    public Bookmark(User user, PracticeQuestion question) {
        this.user = user;
        this.practiceQuestion = question;
    }
}