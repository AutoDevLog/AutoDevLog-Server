package com.server.autodevlog.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private String response;

    @Builder
    public UserResponseDto(ChatGPTResponse gptResponse){
        response = gptResponse.getGPTResponseMessage();
    }
}
