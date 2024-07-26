package com.server.autodevlog.measurement.controller;

import com.server.autodevlog.measurement.dto.EmbedResponseDto;
import com.server.autodevlog.measurement.convertor.EmbeddingConvertor;
import com.server.autodevlog.measurement.service.CosineService;
import com.server.autodevlog.measurement.dto.*;
import com.server.autodevlog.measurement.service.MeasurementService;
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
    private final MeasurementService measurementService;
    @PostMapping("/cosine") // 유저 프롬프트 -> gpt api
    @Operation(summary = "코사인 유사도 추출",description = "Request Body 담겨 있는 issue, inference, solution을 통해 게시글을 생성하고 생성된 게시글을 기반으로 사전에 입력된 게시글의 벡터리스트 와의 코사인 유사도 Response")
    public ResponseEntity<CosineResponseDto> chatCosine(@RequestBody @Valid MeasurementRequestDto request){

        EmbedResponseDto embedResponseDto = measurementService.keywordGeneratedArticleEmbed(request); // 키워드 기반 생성글 워드 임베딩
        EmbedResponseDto embedCompareTargetResponse = measurementService.compareTargetArticleEmbed(request); // 대조 글 워드 임베딩

        // cosine 유사도 비교
        double cosineSimilarity = cosineService.calculateCosineSimilarity(EmbeddingConvertor.EmbedReponseToDoubleList(embedCompareTargetResponse), EmbeddingConvertor.EmbedReponseToDoubleList(embedResponseDto));
        CosineResponseDto cosineResponseDTO = CosineResponseDto.builder().cosineSimilarity(cosineSimilarity).build();
        return ResponseEntity.ok(cosineResponseDTO);
    }
}
