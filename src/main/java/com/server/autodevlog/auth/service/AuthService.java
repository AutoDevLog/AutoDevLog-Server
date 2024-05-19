package com.server.autodevlog.auth.service;

import com.server.autodevlog.global.exception.CustomException;
import com.server.autodevlog.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    public void sendVelogEmail(String email) {
        String query = buildVelogQuery(email);

        WebClient webClient = WebClient.builder()
                .baseUrl("https://v3.velog.io")
                .build();

        ResponseEntity<String> response = webClient.post()
                .uri("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(query)
                .retrieve()
                .onStatus(HttpStatusCode::isError, res -> {
                    throw new CustomException(ErrorCode.VELOG_RESPONSE_ERROR); // Velog 서버 400, 500 예외처리
                })
                .toEntity(String.class)
                .block();
    }

    private String buildVelogQuery(String email) {
        return "{\"query\":\"\\n    mutation sendMail($input: SendMailInput!) " +
                "{\\n  sendMail(input: $input) " +
                "{\\n    registered\\n  }\\n}\\n    \"," +
                "\"variables\":{\"input\":{\"email\":\"" + email + "\"}}}";
    }
}
