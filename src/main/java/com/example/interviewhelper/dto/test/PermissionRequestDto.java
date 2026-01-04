package com.example.interviewhelper.dto.test;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PermissionRequestDto {
    private Long userId;
    private boolean cameraAllowed;
    private boolean micAllowed;
}