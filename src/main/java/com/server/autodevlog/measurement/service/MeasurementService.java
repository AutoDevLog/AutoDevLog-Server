package com.server.autodevlog.measurement.service;

import com.server.autodevlog.global.exception.CustomException;
import com.server.autodevlog.global.exception.ErrorCode;
import com.server.autodevlog.measurement.dto.EmbedResponseDto;
import com.server.autodevlog.measurement.dto.EmbedRequestDto;
import com.server.autodevlog.measurement.dto.MeasurementCompareGptResponseDto;
import com.server.autodevlog.measurement.dto.MeasurementGptRequestDto;
import com.server.autodevlog.measurement.dto.MeasurementRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MeasurementService {
    @Value("${openai.api.model}")
    private String model;
    @Value("${openai.api.url}")
    private String url;

    @Value("${openai.embedding.options.model}")
    private String embedModel;
    @Value("${openai.embedding.url}")
    private String embedUrl;

    private final RestTemplate template;

    public EmbedResponseDto keywordGeneratedArticleEmbed(MeasurementRequestDto request){
        MeasurementGptRequestDto gptRequestDto = MeasurementGptRequestDto.builder()
                .model(model)
                .dto(request)
                .build();
        // 키워드 기반 gpt-api article 생성 request
        MeasurementCompareGptResponseDto compareGptResponse = template.postForObject(url,gptRequestDto, MeasurementCompareGptResponseDto.class);

        Optional.ofNullable(compareGptResponse.getChoices()) //gpt api 무응답 예외 처리
                .orElseThrow(()-> new CustomException(ErrorCode.GPT_API_ERROR));


        // 키워드 기반 gpt-api article 임베딩 Request
        EmbedRequestDto embedRequestDto = EmbedRequestDto.builder()
                .input(compareGptResponse.getGptResponseMessage())
                .model(embedModel)
                .build();

        // 키워드 기반 gpt-api article 임베딩 Response
        EmbedResponseDto embedResponseDto = template.postForObject(embedUrl, embedRequestDto, EmbedResponseDto.class);

        Optional.ofNullable(embedResponseDto.getData()) //embed api 무응답 예외 처리
                .orElseThrow(()-> new CustomException(ErrorCode.EMBED_API_ERROR));

        return embedResponseDto;
    }

    public EmbedResponseDto compareTargetArticleEmbed(MeasurementRequestDto request){
        EmbedRequestDto embedCompareTargetRequest = EmbedRequestDto.builder()
                .input(request.getCompareTarget())
                .model(embedModel)
                .build();

        // 대조 글 워드 임베딩 Response
        EmbedResponseDto embedCompareTargetResponse = template.postForObject(embedUrl, embedCompareTargetRequest, EmbedResponseDto.class);

        //embed api 무응답 예외 처리
        Optional.ofNullable(embedCompareTargetResponse.getData())
                .orElseThrow(()-> new CustomException(ErrorCode.EMBED_API_ERROR));

        return embedCompareTargetResponse;
    }
}
