package com.server.autodevlog.auth.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    String userId;
    String password;
}
