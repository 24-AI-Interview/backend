package com.example.interviewhelper.service;

import com.example.interviewhelper.dto.coverletter.AiRequestDto;
import com.example.interviewhelper.dto.coverletter.CoverLetterRequestDto;
import com.example.interviewhelper.dto.coverletter.CoverLetterResponseDto;
import com.example.interviewhelper.entity.CoverLetter;
import com.example.interviewhelper.entity.User;
import com.example.interviewhelper.repository.CoverLetterRepository;
import com.example.interviewhelper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 읽기 전용 기본 설정 (성능 최적화)
public class CoverLetterService {

    private final CoverLetterRepository coverLetterRepository;
    private final UserRepository userRepository;
    private final Tika tika = new Tika(); // Tika(파일 파싱용)

    // 1. 자기소개서 저장
    @Transactional
    public void createCoverLetter(Long userId, CoverLetterRequestDto requestDto) {
        User user = findUserById(userId);
        CoverLetter coverLetter = new CoverLetter(requestDto.getTitle(), requestDto.getContent(), user);
        coverLetterRepository.save(coverLetter);
    }

    // 2. 내 자기소개서 목록 조회
    public List<CoverLetterResponseDto> getMyCoverLetters(Long userId) {
        User user = findUserById(userId);
        return coverLetterRepository.findAllByUser(user).stream()
                .map(CoverLetterResponseDto::new)
                .collect(Collectors.toList());
    }

    // 3. 자기소개서 상세 조회 (새로 추가)
    public CoverLetterResponseDto getCoverLetter(Long id, Long userId) {
        return new CoverLetterResponseDto(findCoverLetterByIdAndUserId(id, userId));
    }

    // 4. 수정
    @Transactional
    public void updateCoverLetter(Long id, Long userId, CoverLetterRequestDto requestDto) {
        CoverLetter coverLetter = findCoverLetterByIdAndUserId(id, userId);
        coverLetter.update(requestDto.getTitle(), requestDto.getContent());
    }

    // 5. 삭제
    @Transactional
    public void deleteCoverLetter(Long id, Long userId) {
        CoverLetter coverLetter = findCoverLetterByIdAndUserId(id, userId);
        coverLetterRepository.delete(coverLetter);
    }

    // --- AI 기능들 (설계서 반영) ---

    // 6. 분량 및 문법 체크
    public String analyze(Long userId, AiRequestDto dto) {
        return processAiTask(userId, dto, "분석(분량/문법)");
    }

    // 7. 문장 첨삭
    public String revise(Long userId, AiRequestDto dto) {
        return processAiTask(userId, dto, "첨삭");
    }

    // 8. 예상 질문 생성
    public String generateQuestions(Long userId, AiRequestDto dto) {
        return processAiTask(userId, dto, "예상 질문 생성");
    }

    // 9. 요약 및 피드백
    public String summary(Long userId, AiRequestDto dto) {
        return processAiTask(userId, dto, "요약 및 피드백");
    }

    // AI 공통 처리 로직 (중복 제거)
    private String processAiTask(Long userId, AiRequestDto dto, String taskName) {
        String content = extractContent(userId, dto);

        // TODO: 실제 AI API(OpenAI 등)를 호출하는 로직 추가
        // 사용자 존재 여부 확인
        User user = findUserById(userId);

        System.out.println("--- AI " + taskName + " 요청 데이터 ---");
        System.out.println("사용자: " + user.getEmail());
        System.out.println("업무 분야: " + dto.getIndustry());
        System.out.println("직무: " + dto.getJob());
        System.out.println("자소서 내용 일부: " + content.substring(0, Math.min(content.length(), 50)) + "...");

        // TODO: 준비된 데이터를 실제 외부 AI API로 보내서 분석 결과를 받아오는 로직 구현
        return "AI의 " + taskName + " 결과입니다. (현재는 임시 응답)";
    }

    // 자소서 내용 추출 로직 (파일, 텍스트, 저장된 ID 대응)
    private String extractContent(Long userId, AiRequestDto dto) {
        if (dto.getText() != null && !dto.getText().isEmpty()) {
            // 1. 직접 입력된 텍스트가 있으면 사용
            return dto.getText();
        } else if (dto.getFile() != null && !dto.getFile().isEmpty()) {
            // 2. 파일이 업로드 되었으면 파일 안의 텍스트 추출
            try{
                return tika.parseToString(dto.getFile().getInputStream());
            } catch (Exception e){
                throw new IllegalArgumentException("파일을 읽는 중 오류가 발생했습니다: "+e.getMessage());
            }
        } else if (dto.getSavedId() != null) {
            // 3. 저장된 자소서 ID가 있으면 DB에서 해당 자소서 내용 조회
            return findCoverLetterByIdAndUserId(dto.getSavedId(), userId).getContent();
        }
        throw new IllegalArgumentException("분석할 자기소개서 내용이 없습니다.");
    }

    // --- 공통 편의 메서드 ---

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
    }

    private CoverLetter findCoverLetterByIdAndUserId(Long coverLetterId, Long userId) {
        CoverLetter coverLetter = coverLetterRepository.findById(coverLetterId).orElseThrow(
                () -> new IllegalArgumentException("자기소개서를 찾을 수 없습니다.")
        );
        if (!coverLetter.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("본인의 자기소개서만 접근할 수 있습니다.");
        }
        return coverLetter;
    }
}