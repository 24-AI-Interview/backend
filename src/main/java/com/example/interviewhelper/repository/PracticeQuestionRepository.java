package com.example.interviewhelper.repository;

import com.example.interviewhelper.entity.PracticeQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PracticeQuestionRepository extends JpaRepository<PracticeQuestion, Long> {
}