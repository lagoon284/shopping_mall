package org.example.shopping.util.common.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.shopping.authLogin.dto.AuthToken;
import org.example.shopping.authLogin.mapper.AuthTokenMapper;
import org.example.shopping.user.service.UserService;
import org.example.shopping.util.exception.dto.CustomException;
import org.example.shopping.util.exception.enums.ErrorCode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final AuthTokenMapper authTokenMapper;

    // 의존성 주입 생성자.
    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserService userService, AuthTokenMapper authTokenMapper) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.authTokenMapper = authTokenMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 클라이언트 요청 헤더에서 토큰을 추출.
        String token = resolveToken(request);

        if (token != null && !token.isEmpty()) {
            Claims claims;
            String userId;
            String role;

            // 토큰이 유효한지 검증.
            if (!jwtUtil.isTokenExpired(token)) {
                claims = jwtUtil.getClaimsFromToken(token);
            } else {
                // accToken이 유효 하지 않을 경우.
                AuthToken getNewToken = getProtectRefData(token);
                claims = jwtUtil.getClaimsFromToken(getNewToken.getAccessToken());

                // 응답 헤더에 새로 발급된 Access Token을 추가.
                response.setHeader("new-access-token", getNewToken.getAccessToken());
            }

            userId = claims.getSubject();
            role = claims.get("role", String.class);

            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

            // 인증 정보를 생성하여 SecurityContext에 저장.
            Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null, Collections.singletonList(authority));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 다음 필터로 요청을 전달.
        filterChain.doFilter(request, response);
    }

    // request에서 jwt를 찾아서 인식문 자르고 스트링으로 반환.
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        // 헤더가 존재하고, "seokhoAccAuth "로 시작하는지 확인.
        if (StringUtils.hasText(bearerToken) && bearerToken.length() > 15 && bearerToken.startsWith("seokhoAccAuth ")) {
            // "seokhoAccAuth " 접두사를 제거하고 실제 토큰만 반환.
            return bearerToken.substring(14);
        }

        // 토큰이 없는 경우 null을 반환.
        return null;
    }

    public AuthToken getProtectRefData(String token) {

        Optional<AuthToken> authTokenOptional = Optional.ofNullable(authTokenMapper.getTokenByAccToken(token));

        AuthToken getAuthToken = authTokenOptional
                .filter(authToken -> jwtUtil.isTokenExpired(authToken.getRefreshToken()))
                .orElseThrow(() -> new CustomException(ErrorCode.AUTH_REF_SIGNATURE_EXPIRED_ERROR));

        // ref token 유효기간이 유효할 때 token 갱신.
        return userService.refLogin(userService.getAuthInfo(getAuthToken.getRefreshToken()));
    }
}
