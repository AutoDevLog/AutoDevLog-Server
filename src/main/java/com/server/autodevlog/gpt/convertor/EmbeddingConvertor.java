package com.server.autodevlog.gpt.convertor;

import com.server.autodevlog.gpt.dto.EmbedResponse;
import com.server.autodevlog.gpt.dto.UserResponseDto;

import java.util.List;

public class EmbeddingConvertor {

    public static UserResponseDto.Vectorization toVectorization(EmbedResponse embedResponse){
        List<Double> embedding = embedResponse.getData().get(0).getEmbedding();
        return UserResponseDto.Vectorization.builder()
                .vectorList(embedding)
                .build();
    }
}
