package com.server.autodevlog.gpt.dto;

import com.server.autodevlog.gpt.convertor.GptConvertor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private String response;

    @Builder
    public UserResponseDto(ChatGPTResponse gptResponse){
        String convertedGptResponse = GptConvertor.deleteChangeLine(gptResponse.getGPTResponseMessage());
        response = convertedGptResponse;
    }
}
