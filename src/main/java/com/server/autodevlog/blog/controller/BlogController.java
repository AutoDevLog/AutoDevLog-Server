package com.server.autodevlog.blog.controller;

import com.server.autodevlog.auth.domain.Member;
import com.server.autodevlog.gpt.dto.GPTArticleUploadRequestDto;
import com.server.autodevlog.blog.service.BlogService;
import com.server.autodevlog.blog.dto.VelogPostRequestDto;
import com.server.autodevlog.gpt.service.GPTArticleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/blog")
public class BlogController {
    private final BlogService blogService;
    private final GPTArticleService gptArticleService;

    @PostMapping("/velog-post")
    @Operation(summary = "벨로그 포스팅 API", description = "만약 서버에 저장된 벨로그 토큰이 만료되었다면 서버단에서 토큰 업데이트 후 재시도하도록 구현 하였습니다.")
    public ResponseEntity velogPosting(@AuthenticationPrincipal Member member, @RequestBody VelogPostRequestDto velogPostRequestDto) {
        blogService.postToVelog(member, velogPostRequestDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/velog-post-server-upload")
    @Operation(summary = "벨로그 포스팅 API", description = "Was에서 벨로그로 게시글을 업데이트를 하기 위한 용도 입니다. 게시글의 제목과 게시글의 해시코드를 전달해주세면 됩니다.")
    public ResponseEntity velogPostingNew(@AuthenticationPrincipal Member member, @RequestBody GPTArticleUploadRequestDto dto) {
        blogService.postToVelog(member, new VelogPostRequestDto(dto.getTitle(), gptArticleService.findArticleContent(dto)));
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
