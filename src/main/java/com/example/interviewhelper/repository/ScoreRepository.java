package com.example.interviewhelper.repository;

import com.example.interviewhelper.entity.InterviewSession;
import com.example.interviewhelper.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    // 특정 면접 세션에 속한 모든 점수를 찾는 기능
    List<Score> findAllByInterviewSession(InterviewSession session);
}