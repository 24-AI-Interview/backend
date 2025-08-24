package com.example.interviewhelper.repository;

import com.example.interviewhelper.entity.PersonalityTest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; // Optional 임포트 추가

public interface PersonalityTestRepository extends JpaRepository<PersonalityTest, Long> {
    // 검사 이름(name)으로 PersonalityTest 엔티티를 찾는 기능
    Optional<PersonalityTest> findByName(String name);
}