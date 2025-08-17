package com.example.interviewhelper.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CoverLetter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 5000) // 글자 수 제한 늘리기
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩으로 성능 최적화
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 작성자 정보

    public CoverLetter(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}