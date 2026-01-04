package com.example.interviewhelper.repository;

import com.example.interviewhelper.entity.Bookmark;
import com.example.interviewhelper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findAllByUser(User user);
}