package com.example.interviewhelper.repository;

import com.example.interviewhelper.entity.InterviewReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewReportRepository extends JpaRepository<InterviewReport, Long> {
}