package com.example.interviewhelper.dto.test;

import com.example.interviewhelper.entity.TestResult;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TestResultDto {
    private Long resultId;
    private String resultData; // JSON 형태의 원본 결과 데이터
    private String pdfUrl;     // PDF 리포트 파일 경로

    public TestResultDto(TestResult result) {
        this.resultId = result.getId();
        this.resultData = result.getResultData();
        this.pdfUrl = result.getPdfUrl();
    }
}