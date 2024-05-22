package com.server.autodevlog.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UserRequestDto {
    private String issue;
    private String inference;
    private String solution;
}
