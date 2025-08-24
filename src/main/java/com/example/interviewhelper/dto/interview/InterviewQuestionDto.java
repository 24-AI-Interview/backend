package com.example.interviewhelper.dto.interview;

import com.example.interviewhelper.entity.PracticeQuestion;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor // <- 기본 생성자를 만들어주는 Lombok 어노테이션
public class InterviewQuestionDto {
    private Long questionId;
    private String category;
    private String content;

    // PracticeQuestion 엔티티를 받아서 DTO를 만드는 생성자
    public InterviewQuestionDto(PracticeQuestion question) {
        this.questionId = question.getId();
        this.category = question.getCategory();
        this.content = question.getContent();
    }
}