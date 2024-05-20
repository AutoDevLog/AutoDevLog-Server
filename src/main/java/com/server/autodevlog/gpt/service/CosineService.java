package com.server.autodevlog.gpt.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CosineService {
    public double calculateCosineSimilarity(List<Double> vectorA, List<Double> vectorB) {
        // 벡터 길이가 다르면 짧은 벡터를 평균값으로 패딩
        if (vectorA.size() != vectorB.size()) {
            int maxLength = Math.max(vectorA.size(), vectorB.size());
            vectorA = padVectorWithMean(vectorA, maxLength);
            vectorB = padVectorWithMean(vectorB, maxLength);
        }

        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (int i = 0; i < vectorA.size(); i++) {
            dotProduct += vectorA.get(i) * vectorB.get(i);
            normA += Math.pow(vectorA.get(i), 2);
            normB += Math.pow(vectorB.get(i), 2);
        }

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    private static List<Double> padVectorWithMean(List<Double> vector, int length) {
        List<Double> paddedVector = new ArrayList<>(vector);
        double mean = vector.stream().mapToDouble(val -> val).average().orElse(0.0);
        while (paddedVector.size() < length) {
            paddedVector.add(mean);
        }
        return paddedVector;
    }
}

