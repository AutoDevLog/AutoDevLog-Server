package com.server.autodevlog.blog.service;

import com.server.autodevlog.blog.dto.VelogPostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class BlogService {
    public void postToVelog(VelogPostDto velogPostDto) {
        String title = velogPostDto.getTitle();
        String body = velogPostDto.getBody();

        String query = buildVelogQuery(title, body);

        WebClient webClient = WebClient.builder()
                .baseUrl("https://v2.velog.io")
                .build();

        ResponseEntity<String> response = webClient.post()
                .uri("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Cookie", "access_token=" + velogPostDto.getToken() + ";")
                .bodyValue(query)
                .retrieve()
                .toEntity(String.class)
                .block();
    }

    private String buildVelogQuery(String title, String body) {
        return "{\"operationName\":\"WritePost\",\"variables\"" +
                ":{\"title\":\"" + title + "\",\"body\":\"" + body
                + "\",\"tags\":[],\"is_markdown\":true,\"is_temp\":false,\"is_private\":false,\"url_slug\":\""
                + title + "\",\"thumbnail\":null,\"meta\":{\"short_description\":\"" + body + "\"}," +
                "\"series_id\":null,\"token\":null},\"query\":\"mutation " +
                "WritePost($title: String, $body: String, $tags: [String], $is_markdown: Boolean, $is_temp: Boolean, $is_private: Boolean, $url_slug: String, $thumbnail: String, $meta: JSON, $series_id: ID, $token: String) " +
                "{\\n  writePost(title: $title, body: $body, tags: $tags, is_markdown: $is_markdown, is_temp: $is_temp, is_private: $is_private, url_slug: $url_slug, thumbnail: $thumbnail, meta: $meta, series_id: $series_id, token: $token) " +
                "{\\n    id\\n    user {\\n      id\\n      username\\n      __typename\\n    }\\n    url_slug\\n    __typename\\n  }\\n}\\n\"}";
    }
}
