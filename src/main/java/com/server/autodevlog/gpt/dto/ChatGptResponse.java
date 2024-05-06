package com.server.autodevlog.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChatGptResponse {

    private List<Choice> choices = new ArrayList<>();

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Choice {
        private int index;
        private Message message;
    }

    public boolean isEmptyChoiceList(){
        return choices.isEmpty();
    }

    public String getGptResponseMessage(){
        return  choices.get(0).getMessage().getContent();
    }
}
