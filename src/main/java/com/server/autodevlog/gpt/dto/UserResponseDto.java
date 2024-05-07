package com.server.autodevlog.gpt.dto;

import com.server.autodevlog.gpt.convertor.GptConvertor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class UserResponseDto {
    private String response;

    @Builder
    public UserResponseDto(ChatGptResponse gptResponse){
        String convertedGptResponse = GptConvertor.deleteChangeLine(gptResponse.getGptResponseMessage());
        response = convertedGptResponse;
    }
    @Builder
    @Getter
    public static class Vectorization{
        List<Double> vectorList;
    }
}
