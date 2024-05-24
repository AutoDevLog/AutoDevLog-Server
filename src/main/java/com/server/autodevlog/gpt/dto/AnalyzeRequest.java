package com.server.autodevlog.gpt.dto;

import lombok.Getter;

@Getter
public class AnalyzeRequest {
    private String issue;
    private String inference;
    private String solution;
    private String compareTarget;
}
