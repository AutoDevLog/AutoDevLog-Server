package com.server.autodevlog.blog.controller;

import com.server.autodevlog.blog.service.BlogService;
import com.server.autodevlog.blog.dto.VelogPostDto;
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
    public ResponseEntity postTest(@RequestBody VelogPostDto velogPostDto) {
        blogService.postToVelog(velogPostDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
