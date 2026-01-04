package com.example.interviewhelper.dto.prep;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookmarkRequestDto {
    private Long userId;
    private Long questionId;
}