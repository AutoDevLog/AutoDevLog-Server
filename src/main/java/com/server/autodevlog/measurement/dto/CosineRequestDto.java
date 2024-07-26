package com.server.autodevlog.measurement.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CosineRequestDto {
    List<Double> vector1;
    List<Double> vector2;
}
