package com.server.autodevlog.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TempTokenReturnDto {
    String accessToken;
    String refreshToken;
}
