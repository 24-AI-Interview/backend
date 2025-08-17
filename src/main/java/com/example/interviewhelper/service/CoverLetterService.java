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
}