package com.example.interviewhelper.repository;

import com.example.interviewhelper.entity.InterviewSession;
import com.example.interviewhelper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewSessionRepository extends JpaRepository<InterviewSession, Long> {
    // 특정 사용자의 모든 면접 세션을 찾는 기능
    List<InterviewSession> findAllByUser(User user);
}