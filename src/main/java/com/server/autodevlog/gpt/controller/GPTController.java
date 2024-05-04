package com.server.autodevlog.gpt.controller;

import com.server.autodevlog.gpt.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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
        ChatGPTRequest request = ChatGPTRequest.builder()
                .model(model)
                .prompt(dto.getUserPrompt())
                .build();

        ChatGPTResponse response = template.postForObject(url,request,ChatGPTResponse.class);
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .gptResponse(response)
                .build();

        return ResponseEntity.ok(userResponseDto);
    }

}
