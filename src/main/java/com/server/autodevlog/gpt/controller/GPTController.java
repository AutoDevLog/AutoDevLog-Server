package com.server.autodevlog.gpt.controller;

import com.server.autodevlog.global.exception.CustomException;
import com.server.autodevlog.global.exception.ErrorCode;
import com.server.autodevlog.gpt.domain.GPTArticle;
import com.server.autodevlog.gpt.dto.*;
import com.server.autodevlog.gpt.service.GPTArticleService;
import com.server.autodevlog.measurement.service.CosineService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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

    @Value("${openai.embedding.options.model}")
    private String embedModel;
    @Value("${openai.embedding.url}")
    private String embedUrl;

    private final RestTemplate template;
    private final CosineService cosineService;
    private final GPTArticleService gptArticleService;

    @PostMapping("/request") // 유저 프롬프트 -> gpt api
    @Operation(summary = "GPT-API 호출 API",description = "Request Body 담겨 있는 issue, inference, solution을 gpt api에 전달하여 응답값을 String 반환")
    public ResponseEntity<String> chat(@RequestBody @Valid UserRequestDto dto, HttpServletResponse httpServletResponse){

        ChatGptRequest gptRequest = ChatGptRequest.builder() // gpt api request http 바디
                .model(model)
                .dto(dto)
                .build();
        ChatGptResponse gptResponse = template.postForObject(url,gptRequest, ChatGptResponse.class); //gpt api request

        if(gptResponse==null||gptResponse.isEmptyChoiceList()){throw new CustomException(ErrorCode.GPT_API_ERROR);} //gpt api 무응답 예외 처리

        String gptArticleKey = gptArticleService.saveArticle(GPTArticle.builder().content(gptResponse.getGptResponseMessage()).build()); // 레디스 생성 게시글 저장

        httpServletResponse.addCookie(new Cookie("article-hashcode",gptArticleKey)); // 쿠키에 레디스 해쉬값 저장
        return ResponseEntity.ok(gptArticleService.findArticleContent(gptArticleKey)); //  생성된 게시글 + 헤더에 레디스 해시값 response
    }
}
