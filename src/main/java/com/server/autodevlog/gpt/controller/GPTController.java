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

    @PostMapping("/request") // 유저 프롬프트 -> gpt api
    public ResponseEntity<String> chat(@RequestBody UserRequestDto dto){

        ChatGptRequest request = ChatGptRequest.builder() // gpt api request http 바디
                .model(model)
                .dto(dto)
                .build();
        ChatGptResponse response = template.postForObject(url,request, ChatGptResponse.class); //gpt api request

        if(response==null||response.isEmptyChoiceList()){throw new CustomException(ErrorCode.GPT_API_ERROR);} //gpt api 무응답 예외 처리

        return ResponseEntity.ok(response.getGptResponseMessage());
    }

}
