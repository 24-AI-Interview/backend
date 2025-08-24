package com.example.interviewhelper.service;

import com.example.interviewhelper.dto.coverletter.CoverLetterRequestDto;
import com.example.interviewhelper.dto.coverletter.CoverLetterResponseDto;
import com.example.interviewhelper.entity.CoverLetter;
import com.example.interviewhelper.entity.User;
import com.example.interviewhelper.repository.CoverLetterRepository;
import com.example.interviewhelper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoverLetterService {

    private final CoverLetterRepository coverLetterRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createCoverLetter(Long userId, CoverLetterRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
        CoverLetter coverLetter = new CoverLetter(requestDto.getTitle(), requestDto.getContent(), user);
        coverLetterRepository.save(coverLetter);
    }

    public List<CoverLetterResponseDto> getMyCoverLetters(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
        return coverLetterRepository.findAllByUser(user).stream()
                .map(CoverLetterResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateCoverLetter(Long coverLetterId, Long userId, CoverLetterRequestDto requestDto) {
        CoverLetter coverLetter = findCoverLetterByIdAndUserId(coverLetterId, userId);
        coverLetter.update(requestDto.getTitle(), requestDto.getContent());
    }

    @Transactional
    public void deleteCoverLetter(Long coverLetterId, Long userId) {
        CoverLetter coverLetter = findCoverLetterByIdAndUserId(coverLetterId, userId);
        coverLetterRepository.delete(coverLetter);
    }

    // 자기소개서 조회 및 본인 확인을 위한 공통 메소드
    private CoverLetter findCoverLetterByIdAndUserId(Long coverLetterId, Long userId) {
        CoverLetter coverLetter = coverLetterRepository.findById(coverLetterId).orElseThrow(
                () -> new IllegalArgumentException("자기소개서를 찾을 수 없습니다.")
        );
        if (!coverLetter.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인의 자기소개서만 수정/삭제할 수 있습니다.");
        }
        return coverLetter;
    }

    public String analyze(Long userId, String industry, String job, String text, Long savedId, MultipartFile file) {
        String coverLetterContent = "";

        // 사용자 존재 여부 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (text != null && !text.isEmpty()) {
            // 1. 직접 입력된 텍스트가 있으면 사용
            coverLetterContent = text;
        } else if (file != null && !file.isEmpty()) {
            // 2. 파일이 업로드되었으면 파일 안의 텍스트를 추출
            // TODO: 실제 파일(PDF, DOCX) 내용을 파싱하려면 Apache Tika 같은 라이브러리 추가 필요
            coverLetterContent = "[임시] 파일명: " + file.getOriginalFilename();
        } else if (savedId != null) {
            // 3. 저장된 자소서 ID가 있으면 DB에서 해당 자소서 내용을 조회
            CoverLetter savedCoverLetter = findCoverLetterByIdAndUserId(savedId, userId);
            coverLetterContent = savedCoverLetter.getContent();
        }

        if (coverLetterContent.isEmpty()) {
            throw new IllegalArgumentException("분석할 자기소개서 내용이 없습니다.");
        }

        System.out.println("--- AI 분석 요청 데이터 ---");
        System.out.println("사용자: " + user.getEmail());
        System.out.println("업무 분야: " + industry);
        System.out.println("직무: " + job);
        System.out.println("자소서 내용 일부: " + coverLetterContent.substring(0, Math.min(coverLetterContent.length(), 50)) + "...");

        // TODO: 준비된 데이터를 실제 외부 AI API로 보내서 분석 결과를 받아오는 로직 구현
        String aiResult = "AI의 첨삭 결과입니다. (현재는 임시 응답)";

        return aiResult;
    }


}