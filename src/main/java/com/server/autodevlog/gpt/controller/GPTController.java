package com.server.autodevlog.gpt.controller;

import com.server.autodevlog.global.exception.CustomException;
import com.server.autodevlog.global.exception.ErrorCode;
import com.server.autodevlog.gpt.convertor.EmbeddingConvertor;
import com.server.autodevlog.gpt.dto.*;
import com.server.autodevlog.gpt.service.CosineService;
import io.swagger.v3.oas.annotations.Operation;
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
    @PostMapping("/request") // 유저 프롬프트 -> gpt api
    public ResponseEntity<UserResponseDto> chat(@RequestBody UserRequestDto dto){

        ChatGptRequest request = ChatGptRequest.builder() // gpt api request http 바디
                .model(model)
                .prompt(dto.getUserPrompt())
                .build();
        ChatGptResponse response = template.postForObject(url,request, ChatGptResponse.class); //gpt api request

        if(response==null||response.isEmptyChoiceList()){throw new CustomException(ErrorCode.GPT_API_ERROR);} //gpt api 무응답 예외 처리

        UserResponseDto userResponseDto = UserResponseDto.builder() //유저 Response 생성
                .gptResponse(response)
                .build();

        return ResponseEntity.ok(userResponseDto);
    }

    @PostMapping("/embed")
    @Operation(summary = "워드 임베딩 API", description = "Body에 블로그 글을 넣어주세요. 토큰으로 끊어서 벡터리스트를 반환합니다.")
    public ResponseEntity<Word2VecResponseDTO> embed(@RequestBody Word2VecRequestDTO userRequestDto){
        EmbedRequest request = EmbedRequest.builder()
                .input(userRequestDto.getUserPrompt())
                .model(embedModel)
                .build();
        EmbedResponse response = template.postForObject(embedUrl, request, EmbedResponse.class);
        if(response==null||response.isEmptyChoiceList()){throw new CustomException(ErrorCode.EMBED_API_ERROR);} //embed api 무응답 예외 처리
        return ResponseEntity.ok(EmbeddingConvertor.toWord2VecResponseDTO(response));
    }

    @PostMapping("/cosine")
    @Operation(summary = "코사인 유사도 API", description = "Body에 비교하는 벡터리스트들을 넣어주세요. 코사인유사도를 반환합니다.")
    public ResponseEntity<CosineResponseDTO> cosine(@RequestBody CosineRequestDTO cosineRequestDTO){
        double cosineSimilarity = cosineService.calculateCosineSimilarity(cosineRequestDTO.getVector1(), cosineRequestDTO.getVector2());
        CosineResponseDTO cosineResponseDTO = CosineResponseDTO.builder().cosineSimilarity(cosineSimilarity).build();
        return ResponseEntity.ok(cosineResponseDTO);
    }
}
