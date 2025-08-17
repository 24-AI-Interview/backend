package com.example.interviewhelper.dto.coverletter;

import com.example.interviewhelper.entity.CoverLetter;
import lombok.Getter;

@Getter
public class CoverLetterResponseDto {
    private Long id;
    private String title;
    private String content;

    public CoverLetterResponseDto(CoverLetter coverLetter) {
        this.id = coverLetter.getId();
        this.title = coverLetter.getTitle();
        this.content = coverLetter.getContent();
    }
}