package com.server.autodevlog.gpt.controller;

import com.server.autodevlog.global.exception.CustomException;
import com.server.autodevlog.global.exception.ErrorCode;
import com.server.autodevlog.gpt.convertor.EmbeddingConvertor;
import com.server.autodevlog.gpt.dto.*;
import com.server.autodevlog.gpt.service.CosineService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/analyze")
public class AnalyzeController {

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
    @PostMapping("/cosine") // 유저 프롬프트 -> gpt api
    @Operation(summary = "코사인 유사도 추출",description = "Request Body 담겨 있는 issue, inference, solution을 통해 게시글을 생성하고 생성된 게시글을 기반으로 사전에 입력된 게시글의 벡터리스트 와의 코사인 유사도 Response")
    public ResponseEntity<CosineResponseDTO> chatCosine(@RequestBody @Valid AnalyzeRequest request){

        UserRequestDto dto = UserRequestDto.builder()
                .issue(request.getIssue())
                .inference(request.getInference())
                .solution(request.getSolution())
                .build();

        // 유저 프롬프트 request
        ChatGptRequest chatRequest = ChatGptRequest.builder() // gpt api request http 바디
                .model(model)
                .dto(dto)
                .build();
        ChatGptResponse response = template.postForObject(url,chatRequest, ChatGptResponse.class); //gpt api request

        if(response==null||response.isEmptyChoiceList()){throw new CustomException(ErrorCode.GPT_API_ERROR);} //gpt api 무응답 예외 처리

        System.out.println(response.getGptResponseMessage());

        // 유저 프롬프트 워드 임베딩 Request
        EmbedRequest embedRequest = EmbedRequest.builder()
                .input(response.getGptResponseMessage())
                .model(embedModel)
                .build();

        // 유저 프롬프트 워드 임베딩 Response
        EmbedResponse embedResponse = template.postForObject(embedUrl, embedRequest, EmbedResponse.class);
        if(embedResponse==null||embedResponse.isEmptyChoiceList()){throw new CustomException(ErrorCode.EMBED_API_ERROR);} //embed api 무응답 예외 처리

        // 대조 대상글 워드 임베딩 Request
        EmbedRequest embedCompareTargetRequest = EmbedRequest.builder()
                .input(request.getCompareTarget())
                .model(embedModel)
                .build();

        // 대조 대상글 워드 임베딩 Response
        EmbedResponse embedCompareTargetResponse = template.postForObject(embedUrl, embedCompareTargetRequest, EmbedResponse.class);
        if(embedCompareTargetResponse==null||embedCompareTargetResponse.isEmptyChoiceList()){throw new CustomException(ErrorCode.EMBED_API_ERROR);} //embed api 무응답 예외 처리


        // cosine 유사도 request
        double cosineSimilarity = cosineService.calculateCosineSimilarity(EmbeddingConvertor.EmbedReponseToDoubleList(embedCompareTargetResponse), EmbeddingConvertor.EmbedReponseToDoubleList(embedResponse));
        CosineResponseDTO cosineResponseDTO = CosineResponseDTO.builder().cosineSimilarity(cosineSimilarity).build();
        return ResponseEntity.ok(cosineResponseDTO);
    }
}
