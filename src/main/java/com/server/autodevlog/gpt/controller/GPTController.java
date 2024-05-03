package com.server.autodevlog.gpt.controller;

import com.server.autodevlog.gpt.dto.ChatGPTRequest;
import com.server.autodevlog.gpt.dto.ChatGPTResponse;
import com.server.autodevlog.gpt.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @GetMapping("/request")
    public String chat(@RequestBody UserRequestDto dto){
        ChatGPTRequest request = ChatGPTRequest.builder()
                .model(model)
                .prompt(dto.getUserPrompt())
                .build();
        ChatGPTResponse response = template.postForObject(url,request,ChatGPTResponse.class);
        return response.getChoices().get(0).getMessage().getContent();
    }

}
