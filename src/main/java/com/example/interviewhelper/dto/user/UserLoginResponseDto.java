package com.example.interviewhelper.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginResponseDto {
    private UserInfo user;
    private TokenInfo tokens;

    @Getter
    @Builder
    public static class UserInfo {
        private Long id;
        private String name;
        private String email;
        private String lastLoginAt;
    }

    @Getter
    @Builder
    public static class TokenInfo {
        private String accessToken;
        private String refreshToken;
        private int expiresIn;
    }
}