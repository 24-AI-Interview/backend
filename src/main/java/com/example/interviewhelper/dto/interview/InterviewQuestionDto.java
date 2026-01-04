package com.example.interviewhelper.dto.interview;

import com.example.interviewhelper.entity.PracticeQuestion;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InterviewQuestionDto {
    private Long sessionId;  // 추가됨
    private Long questionId;
    private String category;
    private String content;

    // 생성자 수정: sessionId를 추가로 받음
    public InterviewQuestionDto(Long sessionId, PracticeQuestion question) {
        this.sessionId = sessionId;
        this.questionId = question.getId();
        this.category = question.getCategory();
        this.content = question.getContent();
    }
}