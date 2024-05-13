package com.server.autodevlog.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmbedResponse {
    List<DataItem> data;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataItem {
        int index;
        List<Double> embedding;
    }
    public boolean isEmptyChoiceList(){
        return data.isEmpty();
    }

}
