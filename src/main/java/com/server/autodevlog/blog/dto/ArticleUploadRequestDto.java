package com.server.autodevlog.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleUploadRequestDto {
    private String title;
    private String hashCode;
}
