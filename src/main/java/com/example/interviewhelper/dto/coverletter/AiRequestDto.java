package com.example.interviewhelper.dto.coverletter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class AiRequestDto {
    private String industry;  // 업무 분야 (예: IT, 금융)
    private String job;       // 직무 (예: 백엔드 개발자)
    private String text;      // 직접 입력한 자소서 내용 (선택)
    private Long savedId;     // 이미 저장된 자소서 ID를 쓸 경우 (선택)
    private MultipartFile file; // 파일 업로드

}