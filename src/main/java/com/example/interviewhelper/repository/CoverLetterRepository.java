package com.example.interviewhelper.repository;

import com.example.interviewhelper.entity.CoverLetter;
import com.example.interviewhelper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CoverLetterRepository extends JpaRepository<CoverLetter, Long> {
    List<CoverLetter> findAllByUser(User user);
}