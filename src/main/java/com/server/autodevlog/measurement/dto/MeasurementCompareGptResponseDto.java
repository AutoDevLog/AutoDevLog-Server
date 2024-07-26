package com.server.autodevlog.measurement.dto;

import com.server.autodevlog.gpt.dto.ChatGptResponseDto;
import com.server.autodevlog.gpt.dto.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class MeasurementCompareGptResponseDto {
    private List<ChatGptResponseDto.Choice> choices = new ArrayList<>();

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
