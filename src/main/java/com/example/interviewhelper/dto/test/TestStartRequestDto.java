package com.example.interviewhelper.dto.test;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TestStartRequestDto {
    private Long userId;        // 검사를 시작하는 사용자 ID
    private String testType;    // 검사 유형 (예: "BIG5")
}