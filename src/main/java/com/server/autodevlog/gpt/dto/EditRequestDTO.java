package com.server.autodevlog.gpt.dto;

import lombok.Getter;

import java.io.IOException;

@Getter
public class EditRequestDTO {
    String content;
    public void setContent(String content) {
        this.content = content;
    }
}