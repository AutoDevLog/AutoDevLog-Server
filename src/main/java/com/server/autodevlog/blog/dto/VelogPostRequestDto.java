package com.server.autodevlog.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VelogPostRequestDto {
    String title;
    String body;
    String token;
}
