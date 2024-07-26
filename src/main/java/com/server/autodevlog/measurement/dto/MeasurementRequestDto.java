package com.server.autodevlog.measurement.dto;


import com.server.autodevlog.gpt.validation.NullPrompt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class MeasurementRequestDto {
    @NullPrompt
    private String issue;
    @NullPrompt
    private String inference;
    @NullPrompt
    private String solution;
    @NullPrompt
    private String compareTarget;
}
