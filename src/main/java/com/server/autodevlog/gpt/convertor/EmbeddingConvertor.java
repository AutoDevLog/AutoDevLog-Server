package com.server.autodevlog.gpt.convertor;

import com.server.autodevlog.gpt.dto.EmbedResponse;
import com.server.autodevlog.gpt.dto.UserResponseDto;
import com.server.autodevlog.gpt.dto.Word2VecResponseDTO;

import java.util.List;

public class EmbeddingConvertor {

    public static Word2VecResponseDTO toWord2VecResponseDTO(EmbedResponse embedResponse){
        List<Double> embedding = embedResponse.getData().get(0).getEmbedding();
        return Word2VecResponseDTO.builder()
                .vectorList(embedding)
                .build();
    }
}
