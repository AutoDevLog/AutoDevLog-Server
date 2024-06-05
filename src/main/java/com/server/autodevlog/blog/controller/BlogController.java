package com.server.autodevlog.blog.controller;

import com.server.autodevlog.auth.domain.Member;
import com.server.autodevlog.blog.dto.ArticleUploadRequestDto;
import com.server.autodevlog.blog.service.BlogService;
import com.server.autodevlog.blog.dto.VelogPostRequestDto;
import com.server.autodevlog.gpt.service.ArticleService;
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
    private final ArticleService articleService; // 테스트 이후 디펜던시 구조를 바꿀 예정입니다. 이친구가 여깄으면 안된다는 점 잘 인지하고 있습니다.

    @PostMapping("/velog-post")
    @Operation(summary = "벨로그 포스팅 API", description = "벨로그 로그인이 구현되지 않아서, body의 token 값으로 벨로그 access_token 값을 넣어주세요.")
    public ResponseEntity velogPosting(@AuthenticationPrincipal Member member, @RequestBody VelogPostRequestDto velogPostRequestDto) {
        blogService.postToVelog(member, velogPostRequestDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    // WAS에서 redis에서 글을 읽어서 벨로그에 잘 업로드가 되는지 확인하고자 임시로 넣은 컨트롤러입니다. 이후 수정될 예정입니다.
    @PostMapping("/velog-post-server-upload")
    @Operation(summary = "벨로그 포스팅 API", description = "was에서 벨로그로 게시글을 업데이트를 하기 위한 용도 입니다. 아직 테스트 중입니다.")
    public ResponseEntity velogPostingNew(@AuthenticationPrincipal Member member, @RequestBody ArticleUploadRequestDto dto) {
        blogService.postToVelog(member, new VelogPostRequestDto(dto.getTitle(),articleService.findArticle(dto.getHashCode()).getContent()));
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
