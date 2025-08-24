package com.example.interviewhelper.repository;

import com.example.interviewhelper.entity.TestResult;
import com.example.interviewhelper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    // 특정 사용자의 모든 인적성 검사 결과를 찾는 기능
    // TestResult -> testSession -> user 를 통해 사용자를 찾습니다.
    List<TestResult> findAllByTestSession_User(User user);
}