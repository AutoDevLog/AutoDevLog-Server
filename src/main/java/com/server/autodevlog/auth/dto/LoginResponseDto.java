package com.server.autodevlog.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {
    String accessToken;
    String refreshToken;
}
