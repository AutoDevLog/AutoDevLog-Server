package com.server.autodevlog.gpt.dto;

import com.server.autodevlog.gpt.validation.NullPrompt;
import lombok.Getter;

@Getter
public class AnalyzeRequest {
    @NullPrompt
    private String issue;
    @NullPrompt
    private String inference;
    @NullPrompt
    private String solution;
    @NullPrompt
    private String compareTarget;
}
