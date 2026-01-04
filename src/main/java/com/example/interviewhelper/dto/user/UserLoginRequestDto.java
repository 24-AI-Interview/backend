package com.example.interviewhelper.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginRequestDto {
    private String email;
    private String password;
    private boolean remember;
}