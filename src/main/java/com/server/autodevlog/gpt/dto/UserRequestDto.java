package com.server.autodevlog.gpt.dto;

import com.server.autodevlog.gpt.validation.NullPrompt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UserRequestDto {
    @NullPrompt
    private String issue;
    @NullPrompt
    private String inference;
    @NullPrompt
    private String solution;
}
