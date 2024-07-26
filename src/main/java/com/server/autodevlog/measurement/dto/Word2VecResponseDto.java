package com.server.autodevlog.measurement.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Word2VecResponseDto {
    List<Double> vectorList;

}
