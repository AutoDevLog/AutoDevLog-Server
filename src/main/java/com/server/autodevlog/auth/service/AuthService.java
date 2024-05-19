package com.server.autodevlog.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
