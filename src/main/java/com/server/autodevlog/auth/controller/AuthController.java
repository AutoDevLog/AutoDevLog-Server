package com.server.autodevlog.auth.controller;

import com.server.autodevlog.auth.dto.*;
import com.server.autodevlog.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Operation(summary = "우리 서비스 로그인", description = "우리 서비스에 로그인 합니다. 계정이 존재하지 않으면 회원가입 처리합니다.")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = authService.login(loginRequestDto);
        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/link-velog-1")
    @Operation(summary = "벨로그 연동 이메일 받기", description = "우리 서비스 로그인 구현이 끝나면 header에 토큰을 넣도록 할 예정입니다.")
    public ResponseEntity linkVelogStart(@RequestBody VelogEmailRequestDto velogEmailRequestDto) {
        authService.sendVelogEmail(velogEmailRequestDto.getEmail());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/link-velog-2")
    @Operation(summary = "벨로그 연동 완료하기", description = "아직 우리 서비스 로그인 구현이 완료 되지 않아서, 테스트 편의를 위해 임시로 velog access token을 그대로 리턴합니다. 로그인 구현이 완료되면 추후 명세대로 요청과 응답 형식이 변경 될 예정입니다.")
    public ResponseEntity<TempTokenReturnDto> linkVelogEnd(@RequestBody VelogUrlRequestDto velogUrlRequestDto) {
        TempTokenReturnDto tempTokenReturnDto = authService.getVelogAuthToken(velogUrlRequestDto.getAuthUrl());
        return ResponseEntity.ok(tempTokenReturnDto);
    }
}
