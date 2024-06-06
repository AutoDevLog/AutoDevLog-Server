package com.server.autodevlog.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    PROMPT_NULL_ERROR(HttpStatus.BAD_REQUEST,"PROMPT-400","프롬프트에 NULL 값이 존재합니다."),
    GPT_API_ERROR(HttpStatus.SERVICE_UNAVAILABLE,"GPT-503","GPT API가 정상적으로 응답하지 않습니다."),
    GPT_ARTICLE_ERROR(HttpStatus.NOT_FOUND,"GPT-404","GPT API 생성 게시글을 찾지 못했습니다."),
    EMBED_API_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "EMBED-503","EMBED API가 정상적으로 응답하지 않습니다."),
    VELOG_RESPONSE_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "VELOG-503", "Velog 서버가 정상적으로 응답하지 않습니다."),
    VELOG_POSTING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "VELOG-500", "Velog 포스팅에 실패했습니다."),
    VELOG_CODE_ERROR(HttpStatus.BAD_REQUEST, "VELOG-400", "만료되었거나 잘못 된 인증 링크입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
