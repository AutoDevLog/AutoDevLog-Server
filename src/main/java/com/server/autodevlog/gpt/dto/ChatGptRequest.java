package com.server.autodevlog.gpt.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChatGptRequest {
    private String model;
    private List<Message> messages = new ArrayList<>();

    @Builder
    public ChatGptRequest(String model, String prompt) {
        this.model = model;
        this.messages.add(new Message("user", prompt));
    }
}