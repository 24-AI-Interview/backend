package com.example.interviewhelper.dto.interview;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InterviewStartRequestDto {
    private Long userId;
    private Long coverLetterId; // 어떤 자소서를 기반으로 질문을 뽑을지
    private String jobCategory; // 직무 (예: 개발, 마케팅)
}