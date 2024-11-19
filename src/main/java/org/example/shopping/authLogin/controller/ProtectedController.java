package org.example.shopping.authLogin.controller;

import lombok.RequiredArgsConstructor;
import org.example.shopping.authLogin.dto.AuthToken;
import org.example.shopping.authLogin.dto.LoginInfo;
import org.example.shopping.user.dto.User;
import org.example.shopping.util.exception.enums.ErrorCode;
import org.example.shopping.util.exception.CustomException;
import org.example.shopping.user.service.UserService;
import org.example.shopping.util.common.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/protected")
public class ProtectedController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/accData")
    public LoginInfo getProtectedAccData(@RequestHeader("Authorization") String token) {

        if (token != null && token.startsWith("seokhoAccAuth ")) {
            String jwt = token.substring(14);
            try {
                if (jwtUtil.isTokenExpired(jwt)) {
                    return jwtUtil.extractUserObj(jwt);
                }
            } catch (Exception e) {
                // 유효기간이 지났을 때 핸들링.
                throw new CustomException(ErrorCode.AUTH_SIGNATURE_EXPIRED_ERROR);
            }
        }
        throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);

    }

    @PutMapping("/refData")
    public AuthToken getProtectRefData(@RequestHeader("Authorization") String token) {

        if (token != null && token.startsWith("seokhoAccAuth ")) {
            String jwt = token.substring(14);
            return userService.refLogin(userService.getAuthInfo(jwt));
        }
        throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}

// 스크립트에서 핸들링해줘야 함. localstorage 에 저장된 토큰값을 확인하고 그거로 이쪽 컨트롤러를 타서
// 유효기간 체크하고 지났으면 ref로 안지났으면 그대로 로그인 ㄱㄱ.
// ref 타고나서 맵으로 넘겨준거 스크립트에서 localstorage에 넣어서 보관해야함.
// 생각보다 간단한데 생각보다 복잡함....

// db에 토큰 생명주기가 지나면 행을 지우도록하는 로직 필요할 듯? 계속 놔둘건 아니니까? -- 유효기간을 체크하기 때문에 놔둬도 괜찮을거 같음.