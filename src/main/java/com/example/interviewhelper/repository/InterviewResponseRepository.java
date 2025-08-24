package com.example.interviewhelper.repository;

import com.example.interviewhelper.entity.InterviewResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewResponseRepository extends JpaRepository<InterviewResponse, Long> {
}