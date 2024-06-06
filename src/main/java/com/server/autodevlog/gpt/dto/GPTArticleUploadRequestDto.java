package com.server.autodevlog.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GPTArticleUploadRequestDto {
    private String title;
    private String hashCode;
}
