package com.server.autodevlog.auth.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtEnum {

    ACCESS_TOKEN_SUBJECT("AccessToken"),
    REFRESH_TOKEN_SUBJECT("RefreshToken"),
    UUID_CLAIM("uuid"),
    BEARER("Bearer ");

    private final String value;
}
