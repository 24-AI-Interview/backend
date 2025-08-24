package com.example.interviewhelper.repository;

import com.example.interviewhelper.entity.PersonalityTestSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalityTestSessionRepository extends JpaRepository<PersonalityTestSession, Long> {
}