package com.server.autodevlog.blog.service;

import com.server.autodevlog.auth.domain.Member;
import com.server.autodevlog.auth.repository.MemberRepository;
import com.server.autodevlog.blog.dto.VelogPostRequestDto;
import com.server.autodevlog.blog.dto.VelogPostResponseDto;
import com.server.autodevlog.global.exception.CustomException;
import com.server.autodevlog.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final MemberRepository memberRepository;

    @Retryable(maxAttempts = 2, retryFor = CustomException.class)
    public void postToVelog(Member member, VelogPostRequestDto velogPostRequestDto) throws CustomException {

        String query = buildVelogQuery(velogPostRequestDto);
        String cookie = buildCookieString(member);

        WebClient webClient = WebClient.builder()
                .baseUrl("https://v2.velog.io")
                .build();

        ResponseEntity<VelogPostResponseDto> response = sendPostRequest(webClient, query, cookie);

        Optional.ofNullable(response.getBody()) // Response body의 null 체크
                .map(VelogPostResponseDto::getData)
                .map(VelogPostResponseDto.VelogResponseData::getWritePost) // Posting 실패 예외처리
                .orElseThrow(() -> {
                    updateVelogTokens(member, response.getHeaders()); // Token 업데이트
                    throw new CustomException(ErrorCode.VELOG_POSTING_ERROR); // Retry 될 수 있도록 예외 Throw
                });
    }

    private ResponseEntity<VelogPostResponseDto> sendPostRequest(WebClient webClient, String query, String cookie) {
        ResponseEntity<VelogPostResponseDto> response = webClient.post()
                .uri("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Cookie", cookie)
                .bodyValue(query)
                .retrieve()
                .onStatus(HttpStatusCode::isError, res -> {
                    throw new CustomException(ErrorCode.VELOG_RESPONSE_ERROR); // Velog 서버 400, 500 예외처리
                })
                .toEntity(VelogPostResponseDto.class)
                .block();

        return response;
    }

    private String buildVelogQuery(VelogPostRequestDto velogPostRequestDto) {
        String title = velogPostRequestDto.getTitle();
        String body = velogPostRequestDto.getBody().replace("\n", "\\n").replace("\"", "'");
        String shortDescription = body.substring(body.indexOf("#이슈 정의"),20)+"..."; // '#이슈 정의' 키워드 뒤부터 인덱스 20 전까지를 short_description 으로 지정
        return "{\"operationName\":\"WritePost\",\"variables\"" +
                ":{\"title\":\"" + title + "\",\"body\":\"" + body
                + "\",\"tags\":[],\"is_markdown\":true,\"is_temp\":false,\"is_private\":false,\"url_slug\":\""
                + title + "\",\"thumbnail\":null,\"meta\":{\"short_description\":\"" + shortDescription + "\"}," +
                "\"series_id\":null,\"token\":null},\"query\":\"mutation " +
                "WritePost($title: String, $body: String, $tags: [String], $is_markdown: Boolean, $is_temp: Boolean, $is_private: Boolean, $url_slug: String, $thumbnail: String, $meta: JSON, $series_id: ID, $token: String) " +
                "{\\n  writePost(title: $title, body: $body, tags: $tags, is_markdown: $is_markdown, is_temp: $is_temp, is_private: $is_private, url_slug: $url_slug, thumbnail: $thumbnail, meta: $meta, series_id: $series_id, token: $token) " +
                "{\\n    id\\n    user {\\n      id\\n      username\\n      __typename\\n    }\\n    url_slug\\n    __typename\\n  }\\n}\\n\"}";
    }

    private String buildCookieString(Member member) {
        String accessToken = member.getVelogAccessToken();
        String refreshToken = member.getVelogRefreshToken();

        return "access_token=" + accessToken + ";" + "refresh_token=" + refreshToken + ";";
    }

    private void updateVelogTokens(Member member, HttpHeaders httpHeaders) {
        List<String> cookies = httpHeaders.get("Set-Cookie");

        String accessToken = cookies.get(0).split(";")[0].substring(13);
        String refreshToken = cookies.get(1).split(";")[0].substring(14);

        member.setVelogTokens(accessToken, refreshToken);
        memberRepository.save(member);
    }
}
