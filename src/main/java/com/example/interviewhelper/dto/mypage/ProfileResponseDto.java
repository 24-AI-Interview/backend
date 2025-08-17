package com.example.interviewhelper.dto.mypage;

import com.example.interviewhelper.entity.User;
import lombok.Getter;

@Getter
public class ProfileResponseDto {
    private String email;
    private String name;
    private String profileInfo;

    public ProfileResponseDto(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.profileInfo = user.getProfileInfo();
    }
}