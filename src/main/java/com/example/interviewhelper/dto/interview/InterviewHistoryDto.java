package com.example.interviewhelper.dto.interview;

// 마이페이지의 '면접 이력' 탭에 보여줄 과거 면접 기록 정보
import com.example.interviewhelper.entity.InterviewSession;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class InterviewHistoryDto {
    private Long interviewId;
    private String date;
    private int questionCount;
    private double overallScore; // 총점
    private String usedResumeVersion;
    private String status;

    public InterviewHistoryDto(InterviewSession session) {
        this.interviewId = session.getId();
        this.date = session.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        // TODO: 실제 로직에 맞게 데이터 채워넣기
        this.questionCount = 5;
        this.overallScore = 85.5;
        this.usedResumeVersion = "자소서 1차 버전";
        this.status = "분석 완료";
    }
}