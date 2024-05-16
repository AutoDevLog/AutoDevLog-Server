package com.server.autodevlog.gpt.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Word2VecResponseDTO {
    List<Double> vectorList;

}
