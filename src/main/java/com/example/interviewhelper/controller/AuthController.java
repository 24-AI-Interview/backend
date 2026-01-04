package com.example.interviewhelper.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") // 보통 /api/auth를 많이 써!
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Object loginDto) {
        return ResponseEntity.ok("로그인 성공");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("로그아웃 성공");
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Object signupDto) {
        return ResponseEntity.ok("회원가입 완료");
    }
}