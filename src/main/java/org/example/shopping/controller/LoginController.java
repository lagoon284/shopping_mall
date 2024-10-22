package org.example.shopping.controller;

import lombok.RequiredArgsConstructor;
import org.example.shopping.dto.common.AuthToken;
import org.example.shopping.enums.ErrorCode;
import org.example.shopping.exception.CustomException;
import org.example.shopping.service.UserService;
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

        AuthToken token = userService.login(loginRequest.get("userId"), loginRequest.get("pw"));

        if (token == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return token;
    }
}
