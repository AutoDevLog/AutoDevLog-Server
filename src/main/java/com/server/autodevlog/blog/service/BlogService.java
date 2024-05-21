package com.server.autodevlog.blog.service;

import com.server.autodevlog.blog.dto.VelogPostRequestDto;
import com.server.autodevlog.blog.dto.VelogPostResponseDto;
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
public class BlogService {
    public void postToVelog(VelogPostRequestDto velogPostRequestDto) throws CustomException {
        String title = velogPostRequestDto.getTitle();
        String body = velogPostRequestDto.getBody();

        String query = buildVelogQuery(title, body);

        WebClient webClient = WebClient.builder()
                .baseUrl("https://v2.velog.io")
                .build();

        ResponseEntity<VelogPostResponseDto> response = webClient.post()
                .uri("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Cookie", velogPostRequestDto.getToken())
                .bodyValue(query)
                .retrieve()
                .onStatus(HttpStatusCode::isError, res -> {
                    throw new CustomException(ErrorCode.VELOG_RESPONSE_ERROR); // Velog 서버 400, 500 예외처리
                })
                .toEntity(VelogPostResponseDto.class)
                .block();

        Optional.ofNullable(response.getBody()) // response body의 null 체크
                .map(VelogPostResponseDto::getData)
                .map(VelogPostResponseDto.VelogResponseData::getWritePost) // Posting 실패 예외처리
                .orElseThrow(() -> new CustomException(ErrorCode.VELOG_POSTING_ERROR));
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
