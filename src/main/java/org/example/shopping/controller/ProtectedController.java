package org.example.shopping.controller;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.example.shopping.model.common.AuthToken;
import org.example.shopping.model.User;
import org.example.shopping.service.UserService;
import org.example.shopping.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/protected")
public class ProtectedController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/accData")
    public String getProtectedAccData(@RequestHeader("Authorization") String token) {

        try {
            if (token != null && token.startsWith("seokhoAccAuth ")) {
                String jwt = token.substring(14);
                if (!jwtUtil.isTokenExpired(jwt)) {
                    User user = jwtUtil.extractUserObj(jwt);
                    return "Protected data for " + user.toString();
                }
            }
        } catch (SignatureException e) {
            // 인증에 실패했을 때 핸들링.
            return "Authorization 값이 다릅니다.";
        } catch (ExpiredJwtException e) {
            // 유효기간이 지났을 때 핸들링.
            return "다시 로그인 해주세요.";
        }

        return "Unauthorized";
    }

    @PutMapping("/refData")
    public String getProtectRefData(@RequestHeader("Authorization") String token) {

        try {
            if (token != null && token.startsWith("seokhoRefAuth")) {
                String jwt = token.substring(14);
                if (!jwtUtil.isTokenExpired(jwt)) {
                    AuthToken getAuthInfo = userService.getAuthInfo(jwt);

                    if (getAuthInfo == null || !getAuthInfo.getRefreshToken().equals(jwt)) {
                        // 인증에 실패했을 때 핸들링.
                        return "Authorization 값이 다릅니다.";
                    }

                    User getUserInfo = userService.oneUserSelect(getAuthInfo.getUserId());

                    AuthToken retVal = userService.refLogin(getUserInfo);

                    return retVal.toString();
                }
            }
        }   catch (SignatureException e) {
            // 인증에 실패했을 때 핸들링.
            return "인증에 실패하였습니다." + e;
        } catch (ExpiredJwtException e) {
            // 유효기간이 지났을 때 핸들링.
            return "유효기간이 지났습니다." + e;
        }

        return "Authorization 값이 다릅니다.";
    }
}

// 스크립트에서 핸들링해줘야 함. localstorage 에 저장된 토큰값을 확인하고 그거로 이쪽 컨트롤러를 타서
// 유효기간 체크하고 지났으면 ref로 안지났으면 그대로 로그인 ㄱㄱ.
// ref 타고나서 맵으로 넘겨준거 스크립트에서 localstorage에 넣어서 보관해야함.
// 생각보다 간단한데 생각보다 복잡함....

// db에 토큰 생명주기가 지나면 행을 지우도록하는 로직 필요할 듯? 계속 놔둘건 아니니까?