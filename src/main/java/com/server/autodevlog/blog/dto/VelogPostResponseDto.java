package com.server.autodevlog.blog.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class VelogPostResponseDto {
    VelogResponseData data;

    @Getter
    public static class VelogResponseData {
        Map<String, String> writePost;
    }
}
