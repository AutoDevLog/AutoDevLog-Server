package com.server.autodevlog.measurement.service;

import com.server.autodevlog.global.exception.CustomException;
import com.server.autodevlog.global.exception.ErrorCode;
import com.server.autodevlog.gpt.controller.EmbedResponse;
import com.server.autodevlog.measurement.dto.EmbedRequest;
import com.server.autodevlog.measurement.dto.MeasurementCompareGptResponse;
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

    public EmbedResponse keywordGeneratedArticleEmbed(MeasurementRequestDto request){
        MeasurementGptRequestDto gptRequestDto = MeasurementGptRequestDto.builder()
                .model(model)
                .dto(request)
                .build();
        // 키워드 기반 gpt-api article 생성 request
        MeasurementCompareGptResponse compareGptResponse = template.postForObject(url,gptRequestDto,MeasurementCompareGptResponse.class);

        Optional.ofNullable(compareGptResponse.getChoices()) //gpt api 무응답 예외 처리
                .orElseThrow(()-> new CustomException(ErrorCode.GPT_API_ERROR));


        // 키워드 기반 gpt-api article 임베딩 Request
        EmbedRequest embedRequest = EmbedRequest.builder()
                .input(compareGptResponse.getGptResponseMessage())
                .model(embedModel)
                .build();

        // 키워드 기반 gpt-api article 임베딩 Response
        EmbedResponse embedResponse = template.postForObject(embedUrl, embedRequest, EmbedResponse.class);

        Optional.ofNullable(embedResponse.getData()) //embed api 무응답 예외 처리
                .orElseThrow(()-> new CustomException(ErrorCode.EMBED_API_ERROR));

        return embedResponse;
    }

    public EmbedResponse compareTargetArticleEmbed(MeasurementRequestDto request){
        EmbedRequest embedCompareTargetRequest = EmbedRequest.builder()
                .input(request.getCompareTarget())
                .model(embedModel)
                .build();

        // 대조 글 워드 임베딩 Response
        EmbedResponse embedCompareTargetResponse = template.postForObject(embedUrl, embedCompareTargetRequest, EmbedResponse.class);
        //if(embedCompareTargetResponse==null||embedCompareTargetResponse.isEmptyChoiceList()){throw new CustomException(ErrorCode.EMBED_API_ERROR);} //embed api 무응답 예외 처리

        Optional.ofNullable(embedCompareTargetResponse.getData())
                .orElseThrow(()-> new CustomException(ErrorCode.EMBED_API_ERROR));

        return embedCompareTargetResponse;
    }
}
