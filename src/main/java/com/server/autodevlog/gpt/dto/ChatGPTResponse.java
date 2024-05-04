package com.server.autodevlog.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChatGPTResponse {

    private List<Choice> choices;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Choice {
        private int index;
        private Message message;

    }

    public String getGPTResponseMessage(){
        return  choices.get(0).getMessage().getContent();
    }
}
