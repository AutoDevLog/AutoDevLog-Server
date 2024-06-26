package com.server.autodevlog.auth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static com.server.autodevlog.auth.service.JwtEnum.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.access.header}")
    private String accessHeader;

    @Value("${security.jwt.refresh.header}")
    private String refreshHeader;

    @Value("${security.jwt.access.expire-length}")
    private String accessTokenExpirationPeriod;

    @Value("${security.jwt.refresh.expire-length}")
    private String refreshTokenExpirationPeriod;

    public String createAccessToken(String userId) {
        try {
            Date now = new Date();
            return JWT.create()
                    .withSubject(ACCESS_TOKEN_SUBJECT.getValue())
                    .withExpiresAt(new Date(now.getTime() + Long.parseLong(accessTokenExpirationPeriod)))
                    .withClaim(UUID_CLAIM.getValue(), userId)
                    .sign(Algorithm.HMAC512(this.secretKey));
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
            return null;
        }

    }

    public String createRefreshToken(String userId) {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT.getValue())
                .withClaim(UUID_CLAIM.getValue(), userId)
                .withExpiresAt(new Date(now.getTime() + Long.parseLong(refreshTokenExpirationPeriod)))
                .sign(Algorithm.HMAC512(this.secretKey));
    }

    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER.getValue()))
                .map(refreshToken -> refreshToken.replace(BEARER.getValue(), ""));
    }

    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER.getValue()))
                .map(refreshToken -> refreshToken.replace(BEARER.getValue(), ""));
    }

    public boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
            return false;
        }
    }

    // 토큰에서 유저 아이디 추출
    public Optional<String> extractUuid(String accessToken) {
        try {
            // 토큰 유효성 검사하는 데에 사용할 알고리즘이 있는 JWT verifier builder 반환
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                    .build() // 반환된 빌더로 JWT verifier 생성
                    .verify(accessToken) // accessToken을 검증하고 유효하지 않다면 예외 발생
                    .getClaim(UUID_CLAIM.getValue())
                    .asString());
        } catch (Exception e) {
            log.error("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

}
