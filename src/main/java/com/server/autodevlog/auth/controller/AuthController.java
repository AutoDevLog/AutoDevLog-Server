package com.server.autodevlog.auth.controller;

import com.server.autodevlog.auth.domain.Member;
import com.server.autodevlog.auth.dto.*;
import com.server.autodevlog.auth.service.AuthService;
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
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "우리 서비스 로그인", description = "계정이 존재하지 않으면 자동으로 회원가입 처리합니다.")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = authService.login(loginRequestDto);
        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/link-velog-1")
    @Operation(summary = "벨로그 연동 이메일 받기", description = "우리 서비스 로그인을 한 뒤 호출해주세요.")
    public ResponseEntity linkVelogStart(@AuthenticationPrincipal Member member, @RequestBody VelogEmailRequestDto velogEmailRequestDto) {
        authService.sendVelogEmail(velogEmailRequestDto.getEmail());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/link-velog-2")
    @Operation(summary = "벨로그 연동 완료하기", description = "link-velog-1 API를 통해 얻은 링크를 입력하세요.")
    public ResponseEntity linkVelogEnd(@AuthenticationPrincipal Member member, @RequestBody VelogUrlRequestDto velogUrlRequestDto) {
        authService.getVelogAuthToken(member, velogUrlRequestDto.getAuthUrl());
        return new ResponseEntity(HttpStatus.OK);
    }
}
