package com.server.autodevlog.gpt.controller;

import com.server.autodevlog.global.exception.CustomException;
import com.server.autodevlog.global.exception.ErrorCode;
import com.server.autodevlog.gpt.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gpt")
public class GPTController {

    @Value("${openai.api.model}")
    private String model;
    @Value("${openai.api.url}")
    private String url;

    private final RestTemplate template;

    @PostMapping("/request")
    public ResponseEntity<UserResponseDto> chat(@RequestBody UserRequestDto dto){

        ChatGptRequest request = ChatGptRequest.builder()
                .model(model)
                .prompt(dto.getUserPrompt())
                .build();
        ChatGptResponse response = template.postForObject(url,request, ChatGptResponse.class);

        if(response==null||response.isEmptyChoiceList()){throw new CustomException(ErrorCode.GPT_API_ERROR);}

        UserResponseDto userResponseDto = UserResponseDto.builder()
                .gptResponse(response)
                .build();
        return ResponseEntity.ok(userResponseDto);
    }

}
