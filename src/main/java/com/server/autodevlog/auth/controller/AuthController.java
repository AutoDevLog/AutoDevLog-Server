package com.server.autodevlog.auth.controller;

import com.server.autodevlog.auth.dto.VelogEmailRequestDto;
import com.server.autodevlog.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/link-velog-1")
    @Operation(summary = "벨로그 연동 이메일 받기", description = "우리 서비스 로그인 구현이 끝나면 header에 토큰을 넣도록 할 예정입니다.")
    public void linkVelogStart(@RequestBody VelogEmailRequestDto velogEmailRequestDto) {
        authService.sendVelogEmail(velogEmailRequestDto.getEmail());
    }
}
