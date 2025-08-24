package com.example.interviewhelper.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class PersonalityTestSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    private PersonalityTest personalityTest;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "testSession", cascade = CascadeType.ALL)
    private TestResult testResult;

    @Builder
    public PersonalityTestSession(User user, PersonalityTest personalityTest) {
        this.user = user;
        this.personalityTest = personalityTest;
        this.createdAt = LocalDateTime.now();
    }
}