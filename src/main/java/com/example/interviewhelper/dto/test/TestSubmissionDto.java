package com.example.interviewhelper.dto.test;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TestSubmissionDto {
    private Long testSessionId;
    private Long questionId;
    private String answer;      // 텍스트 답변
    private int selectedOption; // 선택한 번호 (예: 1~5점)
}