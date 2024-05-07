package com.server.autodevlog.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    GPT_API_ERROR(HttpStatus.SERVICE_UNAVAILABLE,"GPT-503","GPT API가 정상적으로 응답하지 않습니다."),
    EMBED_API_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "EMBED-503","EMBED API가 정상적으로 응답하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
