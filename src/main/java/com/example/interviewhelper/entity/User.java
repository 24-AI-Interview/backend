package com.example.interviewhelper.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users") // DB 테이블 이름을 'users'로 지정
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // --- 2단계(마이페이지)에서 추가될 필드 ---
    private String name; // 이름
    private String profileInfo; // 학력, 경력 등 기타 정보

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // 마이페이지 정보 업데이트를 위한 메소드
    public void updateProfile(String name, String profileInfo) {
        this.name = name;
        this.profileInfo = profileInfo;
    }
}