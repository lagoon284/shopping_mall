package org.example.shopping.authLogin.controller;

import lombok.RequiredArgsConstructor;
import org.example.shopping.authLogin.dto.AuthToken;
import org.example.shopping.authLogin.dto.LoginInfo;
import org.example.shopping.authLogin.mapper.AuthTokenMapper;
import org.example.shopping.util.exception.enums.ErrorCode;
import org.example.shopping.util.exception.dto.CustomException;
import org.example.shopping.user.service.UserService;
import org.example.shopping.util.common.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/protected")
public class ProtectedController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthTokenMapper authTokenMapper;

    @PostMapping("/accData")
    public LoginInfo getProtectedAccData(@RequestHeader("Authorization") String token) {

        if (token != null && token.startsWith("seokhoAccAuth ")) {
            String jwt = token.substring(14);

            LoginInfo userInfo;
            // acc token이 만료되지 않았을 때.
            if (jwtUtil.isTokenExpired(jwt)) {
                userInfo = jwtUtil.extractUserObj(jwt);
                userInfo.setAccessToken(jwt);
            } else {
                AuthToken getNewToken = getProtectRefData(jwt);
                userInfo = jwtUtil.extractUserObj(getNewToken.getAccessToken());
                userInfo.setAccessToken(getNewToken.getAccessToken());
            }
            return userInfo;
        }
        throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);

    }

    public AuthToken getProtectRefData(String token) {

        if (token != null) {
            Optional<AuthToken> authTokenOptional = Optional.ofNullable(authTokenMapper.getTokenByAccToken(token));

            AuthToken getAuthToken = authTokenOptional
                    .filter(authToken -> jwtUtil.isTokenExpired(authToken.getRefreshToken()))
                    .orElseThrow(() -> new CustomException(ErrorCode.AUTH_REF_SIGNATURE_EXPIRED_ERROR));

            // ref token 유효기간이 유효할 때 token 갱신.
            return userService.refLogin(userService.getAuthInfo(getAuthToken.getRefreshToken()));
        }
        throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    // 수정 전 소스
    /*@PutMapping("/refData")
    public AuthToken getProtectRefData(@RequestHeader("Authorization") String token) {

        if (token != null && token.startsWith("seokhoRefAuth ")) {
            String jwt = token.substring(14);

            // ref token 유효기간 지났을 때,
            if (jwtUtil.isTokenExpired(jwt)) {
                AuthToken authTokenData = authTokenMapper.getToken(jwt);

                if (authTokenData == null || authTokenData.getRefreshToken() == null || authTokenData.getRefreshToken().isEmpty()) {
                    throw new CustomException(ErrorCode.AUTH_SIGNATURE_EXPIRED_ERROR);
                }

                String getRefToken = authTokenData.getRefreshToken();

                // ref token 유효기간 확인.
                if (!jwtUtil.isTokenExpired(getRefToken)) {
                    throw new CustomException(ErrorCode.AUTH_REF_SIGNATURE_EXPIRED_ERROR);
                }

                // ref token 유효기간이 유효할 때 token 갱신.
                return userService.refLogin(userService.getAuthInfo(jwt));
            }
        }
        throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
    }*/
}

// 스크립트에서 핸들링해줘야 함. localstorage 에 저장된 토큰값을 확인하고 그거로 이쪽 컨트롤러를 타서
// 유효기간 체크하고 지났으면 ref로 안지났으면 그대로 로그인 ㄱㄱ.
// ref 타고나서 맵으로 넘겨준거 스크립트에서 localstorage에 넣어서 보관해야함.
// 생각보다 간단한데 생각보다 복잡함....

// db에 토큰 생명주기가 지나면 행을 지우도록하는 로직 필요할 듯? 계속 놔둘건 아니니까? -- 유효기간을 체크하기 때문에 놔둬도 괜찮을거 같음.