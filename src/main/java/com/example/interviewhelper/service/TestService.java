package com.example.interviewhelper.service;

import com.example.interviewhelper.dto.test.*;
import com.example.interviewhelper.entity.*;
import com.example.interviewhelper.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestService {

    private final UserRepository userRepository;
    private final PersonalityTestSessionRepository testSessionRepository;
    private final TestResultRepository testResultRepository;
    private final PersonalityTestRepository personalityTestRepository;

    // 1. 검사 목록 조회
    public List<String> getAllTestTypes() {
        return personalityTestRepository.findAll().stream()
                .map(PersonalityTest::getName)
                .collect(Collectors.toList());
    }

    // 2. 권한 저장
    @Transactional
    public void savePermissions(PermissionRequestDto dto) {
        // 실제로는 User 엔티티나 Session 엔티티에 저장하는 로직이 들어갈 수 있어
        System.out.println("권한 저장: Camera=" + dto.isCameraAllowed() + ", Mic=" + dto.isMicAllowed());
    }

    // 3. 검사 시작
    @Transactional
    public Long startTest(TestStartRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        PersonalityTest test = personalityTestRepository.findByName(requestDto.getTestType())
                .orElseThrow(() -> new IllegalArgumentException("해당 검사 유형이 없습니다."));

        PersonalityTestSession session = PersonalityTestSession.builder()
                .user(user)
                .personalityTest(test)
                .build();

        return testSessionRepository.save(session).getId();
    }

    // 4. 응답 제출
    @Transactional
    public void submitAnswer(TestSubmissionDto dto) {
        // TODO: QuestionResponse 같은 엔티티를 만들어 DB에 저장하는 로직 추가 가능
        System.out.println("응답 수신 - 세션: " + dto.getTestSessionId() + ", 문항: " + dto.getQuestionId());
    }

    // 5. 표정 분석
    @Transactional
    public void analyzeFaceEmotion(Long sessionId, MultipartFile file) {
        // TODO: 여기서 AI 엔진으로 파일을 보내서 분석 결과를 받아오는 로직이 들어갈 자리야
        System.out.println("표정 분석 요청 수신 - 세션: " + sessionId + ", 파일명: " + file.getOriginalFilename());
    }

    // 6. 검사 종료 및 결과 생성
    @Transactional
    public TestResultDto completeTest(Long sessionId) {
        PersonalityTestSession session = testSessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("세션을 찾을 수 없습니다."));

        // 임시 결과 데이터 생성 (나중에 진짜 분석 로직으로 대체)
        String resultJson = "{\"score\": 88, \"traits\": [\"열정적\", \"논리적\"]}";

        TestResult result = TestResult.builder()
                .testSession(session)
                .resultData(resultJson)
                .pdfUrl("https://example.com/reports/test_" + sessionId + ".pdf")
                .build();

        testResultRepository.save(result);
        return new TestResultDto(result);
    }

    // 7. 결과 조회
    public TestResultDto getTestResult(Long resultId) {
        TestResult result = testResultRepository.findById(resultId)
                .orElseThrow(() -> new IllegalArgumentException("결과를 찾을 수 없습니다."));
        return new TestResultDto(result);
    }

    // 8. 마이페이지 저장
    @Transactional
    public void saveResultToMyPage(Long resultId) {
        // 이미 TestResult에 저장되어 있으므로, 필요시 '저장 여부' 필드를 true로 바꾸는 식의 로직 추가
        System.out.println("결과 " + resultId + "번을 사용자의 보관함에 고정합니다.");
    }

    // 9. PDF URL 조회
    public String getPdfUrl(Long resultId) {
        TestResult result = testResultRepository.findById(resultId)
                .orElseThrow(() -> new IllegalArgumentException("결과를 찾을 수 없습니다."));
        return result.getPdfUrl();
    }
}