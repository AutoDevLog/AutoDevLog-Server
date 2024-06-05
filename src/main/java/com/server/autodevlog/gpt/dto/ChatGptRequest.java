package com.server.autodevlog.gpt.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChatGptRequest {
    private String model;
    private List<Message> messages = new ArrayList<>();
    private final double temperature = 0.2;

    @Builder
    public ChatGptRequest(String model, UserRequestDto dto) {
        this.model = model;
        messages.add(new Message("system","language : Korean"));
        messages.add(new Message("system","Responding in Text format"));
        messages.add(new Message("system","write to post troubleshooting blog, citation official documentation"));
        messages.add(new Message("system","present example code and solution for the requested issue"));
        messages.add(new Message("system","글을 작성할때 평서체를 사용해서 글을 작성해줘")); // 평서체 테스트 적용
        messages.add(new Message("system","Format: \" #이슈 정의 \"\n, \" #issue example code \" \n,\"#원인 추론 \" \n,\"#해결 방법\"\n, \"#solution example code \" "));
        messages.add(new Message("user","start keyword(issue): "+ dto.getIssue()));
        messages.add(new Message("user","middle keyword(inference): "+dto.getInference()));
        messages.add(new Message("user","end keyword(solution): "+dto.getSolution()));
    }
}