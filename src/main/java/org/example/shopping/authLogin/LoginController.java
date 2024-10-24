package org.example.shopping.authLogin;

import lombok.RequiredArgsConstructor;
import org.example.shopping.util.exception.ErrorCode;
import org.example.shopping.util.exception.CustomException;
import org.example.shopping.funcBase.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    // 로그인 하는 컨트롤러... service는 userService를 사용함....
    // 걍 유저 컨트롤러에 만들까...
    @PutMapping("/login")
    public AuthToken login(@RequestBody Map<String, String> loginRequest) {

        try {
            boolean loginParamCheck = loginRequest.get("userId").isEmpty()
                    || loginRequest.get("pw").isEmpty();

            if (loginParamCheck) {
                throw new CustomException(ErrorCode.INVALID_PARAMETER);
            }
        } catch (NullPointerException e) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }

        return userService.login(loginRequest.get("userId"), loginRequest.get("pw"));
    }
}
