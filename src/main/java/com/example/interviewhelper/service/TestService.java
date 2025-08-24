package com.example.interviewhelper.service;

import com.example.interviewhelper.dto.test.TestResultDto;
import com.example.interviewhelper.dto.test.TestStartRequestDto;
import com.example.interviewhelper.dto.test.TestSubmissionDto;
import com.example.interviewhelper.entity.PersonalityTest;
import com.example.interviewhelper.entity.TestResult;
import com.example.interviewhelper.entity.PersonalityTestSession;
import com.example.interviewhelper.entity.User;
import com.example.interviewhelper.repository.TestResultRepository;
import com.example.interviewhelper.repository.PersonalityTestRepository;
import com.example.interviewhelper.repository.PersonalityTestSessionRepository;
import com.example.interviewhelper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TestService {

    private final UserRepository userRepository;
    private final PersonalityTestSessionRepository testSessionRepository;
    private final TestResultRepository testResultRepository; // 이름 수정
    private final PersonalityTestRepository personalityTestRepository; // Repository 추가

    // 1. 검사 시작
    @Transactional
    public Long startTest(TestStartRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // (수정) 문자열(testType)로 실제 PersonalityTest 엔티티를 DB에서 찾아옴
        PersonalityTest test = personalityTestRepository.findByName(requestDto.getTestType())
                .orElseThrow(() -> new IllegalArgumentException("해당 이름의 검사를 찾을 수 없습니다."));

        PersonalityTestSession session = PersonalityTestSession.builder()
                .user(user)
                .personalityTest(test) // (수정) 찾은 엔티티를 넣어줌
                .build();

        PersonalityTestSession savedSession = testSessionRepository.save(session);
        return savedSession.getId();
    }

    // 2. 문항 응답 제출 (지금은 제출 사실만 확인)
    public void submitAnswer(TestSubmissionDto submissionDto) {
        System.out.println("세션 ID " + submissionDto.getTestSessionId() + "의 답변 수신: "
                + submissionDto.getQuestionId() + "번 문항 - " + submissionDto.getAnswer());
        // TODO: 수신된 답변들을 임시로 저장하거나 처리하는 로직 추가
    }

    // 3. 검사 종료 및 결과 생성
    @Transactional
    public TestResultDto completeTest(Long testSessionId) {
        PersonalityTestSession session = testSessionRepository.findById(testSessionId)
                .orElseThrow(() -> new IllegalArgumentException("검사 세션을 찾을 수 없습니다."));

        // TODO: 제출된 모든 답변을 기반으로 인성 검사 결과 분석 로직 실행
        String resultDataJson = "{\"mbti\":\"ENTP\", \"score\":95}"; // 임시 결과 데이터

        TestResult result = TestResult.builder() // 이름 수정
                .testSession(session)
                .resultData(resultDataJson)
                .build();

        testResultRepository.save(result);

        return new TestResultDto(result);
    }

    // 4. 결과 조회
    public TestResultDto getTestResult(Long resultId) {
        TestResult result = testResultRepository.findById(resultId) // 이름 수정
                .orElseThrow(() -> new IllegalArgumentException("검사 결과를 찾을 수 없습니다."));
        return new TestResultDto(result);
    }
}