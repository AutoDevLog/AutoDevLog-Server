package com.server.autodevlog.gpt.dto;

import lombok.Getter;

@Getter
public class UserRequestDto {
    private String issue;
    private String inference;
    private String solution;
}
