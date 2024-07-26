package com.server.autodevlog.measurement.convertor;

import com.server.autodevlog.measurement.dto.EmbedResponseDto;
import com.server.autodevlog.measurement.dto.Word2VecResponseDto;

import java.util.List;

public class EmbeddingConvertor {

    public static Word2VecResponseDto toWord2VecResponseDTO(EmbedResponseDto embedResponseDto){
        List<Double> embedding = embedResponseDto.getData().get(0).getEmbedding();
        return Word2VecResponseDto.builder()
                .vectorList(embedding)
                .build();
    }

    public static String doubleQuotationEscape(String target){
        return target.replace("\"","\\\"");
    }

    public static List<Double> EmbedReponseToDoubleList(EmbedResponseDto response){
        return response.getData().get(0).getEmbedding();
    }
}
