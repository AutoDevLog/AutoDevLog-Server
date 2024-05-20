package com.server.autodevlog.gpt.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CosineRequestDTO {
    List<Double> vector1;
    List<Double> vector2;
}
