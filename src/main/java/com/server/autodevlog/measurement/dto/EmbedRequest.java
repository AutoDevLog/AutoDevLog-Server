package com.server.autodevlog.measurement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class EmbedRequest {
    String input;
    String model;
}
