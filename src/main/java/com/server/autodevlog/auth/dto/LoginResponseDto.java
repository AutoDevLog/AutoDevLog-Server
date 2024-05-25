package com.server.autodevlog.auth.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    String accessToken;
    String refreshToken;
}
