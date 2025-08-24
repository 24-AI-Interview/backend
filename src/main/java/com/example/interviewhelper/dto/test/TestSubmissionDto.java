package com.example.interviewhelper.dto.test;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TestSubmissionDto {
    private Long testSessionId; // 현재 진행 중인 검사 세션 ID
    private Long questionId;    // 답변하는 문항 ID
    private String answer;      // 문항에 대한 답변 내용
}