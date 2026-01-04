package com.example.interviewhelper.service;

import com.example.interviewhelper.dto.prep.PrepQuestionDto;
import com.example.interviewhelper.entity.Bookmark;
import com.example.interviewhelper.entity.PracticeQuestion;
import com.example.interviewhelper.entity.User;
import com.example.interviewhelper.repository.BookmarkRepository;
import com.example.interviewhelper.repository.PracticeQuestionRepository;
import com.example.interviewhelper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PrepService {

    private final PracticeQuestionRepository practiceQuestionRepository;
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;

    // 1. 질문 예시 조회
    public List<PrepQuestionDto> getSampleQuestions() {
        return practiceQuestionRepository.findAll().stream()
                .map(PrepQuestionDto::new) // DTO에 생성자 추가 필요!
                .collect(Collectors.toList());
    }

    // 2. 즐겨찾기 목록 조회
    public List<PrepQuestionDto> getBookmarkList(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return bookmarkRepository.findAllByUser(user).stream()
                .map(bookmark -> new PrepQuestionDto(bookmark.getPracticeQuestion()))
                .collect(Collectors.toList());
    }

    // 3. 즐겨찾기 추가
    @Transactional
    public void addBookmark(Long userId, Long questionId) {
        User user = userRepository.findById(userId).orElseThrow();
        PracticeQuestion question = practiceQuestionRepository.findById(questionId).orElseThrow();

        Bookmark bookmark = new Bookmark(user, question);
        bookmarkRepository.save(bookmark);
    }

    // 4. 즐겨찾기 삭제
    @Transactional
    public void deleteBookmark(Long bookmarkId) {
        bookmarkRepository.deleteById(bookmarkId);
    }
}