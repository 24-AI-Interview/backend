package com.example.interviewhelper.dto.interview;

import com.example.interviewhelper.entity.InterviewReport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class InterviewReportDto {
    private Long reportId;
    private String comprehensiveFeedback;
    private LocalDateTime createdAt;

    public InterviewReportDto(InterviewReport report) {
        this.reportId = report.getId();
        this.comprehensiveFeedback = report.getComprehensiveFeedback();
        this.createdAt = report.getCreatedAt();
    }
}