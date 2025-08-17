package com.example.interviewhelper.controller;

import com.example.interviewhelper.dto.user.UserLoginRequestDto;
import com.example.interviewhelper.dto.user.UserSignupRequestDto;
import com.example.interviewhelper.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequestDto requestDto) {
        String token = userService.login(requestDto);
        return ResponseEntity.ok(token);
    }
}