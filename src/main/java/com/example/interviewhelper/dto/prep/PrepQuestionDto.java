package com.example.interviewhelper.dto.prep;

import com.example.interviewhelper.entity.PracticeQuestion; // 엔티티 임포트 확인
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PrepQuestionDto {
    private Long id;
    private String category;
    private String content;

    // 엔티티를 DTO로 변환해주는 생성자 추가
    public PrepQuestionDto(PracticeQuestion question) {
        this.id = question.getId();
        this.category = question.getCategory();
        this.content = question.getContent();
    }
}