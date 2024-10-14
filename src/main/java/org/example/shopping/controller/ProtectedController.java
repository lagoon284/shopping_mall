package org.example.shopping.controller;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.http.HttpServletResponse;
import org.example.shopping.model.AuthToken;
import org.example.shopping.model.User;
import org.example.shopping.model.api.ApiRes;
import org.example.shopping.service.UserService;
import org.example.shopping.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/protected")
public class ProtectedController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/accData")
    public ResponseEntity<String> getProtectedAccData(@RequestHeader("Authorization") String token) {
        try {
            if (token != null && token.startsWith("seokhoAccAuth ")) {
                String jwt = token.substring(14);
                if (!jwtUtil.isTokenExpired(jwt)) {
                    User user = jwtUtil.extractUserObj(jwt);
                    return ResponseEntity.ok("Protected data for " + user.toString());
                }
            }
        } catch (SignatureException e) {
            // 인증에 실패했을 때 핸들링.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization 값이 다릅니다.");
        } catch (ExpiredJwtException e) {
            // 유요기간이 지났을 때 핸들링.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("다시 로그인 해주세요.");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }

    @PostMapping("/refData")
    public ResponseEntity<ApiRes<Map<String, String>>> getProtectRefData(@RequestHeader("Authorization") String token) {

        try {
            if (token != null && token.startsWith("seokhoRefAuth")) {
                String jwt = token.substring(14);
                if (jwtUtil.isTokenExpired(jwt)) {
                    AuthToken getAuthInfo = userService.getAuthInfo(jwt);

                    if (getAuthInfo == null || !getAuthInfo.getRefreshToken().equals(jwt)) {
                        // 인증에 실패했을 때 핸들링.
                        return ResponseEntity
                                .status(HttpStatus.UNAUTHORIZED)
                                .body(ApiRes.diyResult("Authorization 값이 다릅니다.", null));
                    }

                    User getUserInfo = userService.oneUserSelect(getAuthInfo.getUserId());

                    Map<String, String> newToken = userService.refLogin(getUserInfo);

                    return ResponseEntity
                            .ok(ApiRes.diyResult("JWT가 성공적으로 갱신되었습니다. => getProtectRefData", newToken));
                }
            }
        }   catch (SignatureException e) {
            // 인증에 실패했을 때 핸들링.
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ApiRes.diyResult("Authorization 값이 다릅니다.", null));
        } catch (ExpiredJwtException e) {
            // 유요기간이 지났을 때 핸들링.
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ApiRes.diyResult("다시 로그인 해주세요.", null));
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiRes.diyResult("Authorization 값이 다릅니다.", null));
    }
}

// 스크립트에서 핸들링해줘야 함. localstorage 에 저장된 토큰값을 확인하고 그거로 이쪽 컨트롤러를 타서
// 유효기간 체크하고 지났으면 ref로 안지났으면 그대로 로그인 ㄱㄱ.
// ref 타고나서 맵으로 넘겨준거 스크립트에서 localstorage에 넣어서 보관해야함.
// 생각보다 간단한데 생각보다 복잡함....

// db에 토큰 생명주기가 지나면 행을 지우도록하는 로직 필요할 듯? 계속 놔둘건 아니니까?