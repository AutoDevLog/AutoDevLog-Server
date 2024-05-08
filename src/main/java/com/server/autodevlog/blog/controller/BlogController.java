package com.server.autodevlog.blog.controller;

import com.server.autodevlog.blog.service.BlogService;
import com.server.autodevlog.blog.dto.VelogPostRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/blog")
public class BlogController {
    private final BlogService blogService;

    @PostMapping("/velog-post")
    @Operation(summary = "벨로그 포스팅 API", description = "벨로그 로그인이 구현되지 않아서, body의 token 값으로 벨로그 access_token 값을 넣어주세요.")
    public ResponseEntity velogPosting(@RequestBody VelogPostRequestDto velogPostRequestDto) {
        blogService.postToVelog(velogPostRequestDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
