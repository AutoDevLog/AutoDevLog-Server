package com.server.autodevlog.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    GPT_API_ERROR(HttpStatus.SERVICE_UNAVAILABLE,"GPT-503","GPT API가 정상적으로 응답하지 않습니다."),
    VELOG_RESPONSE_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "VELOG-503", "Velog 서버가 정상적으로 응답하지 않습니다."),
    VELOG_POSTING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "VELOG-500", "Velog 포스팅에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
