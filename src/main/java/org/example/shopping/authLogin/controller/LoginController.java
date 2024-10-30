package org.example.shopping.authLogin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shopping.authLogin.dto.AuthToken;
import org.example.shopping.authLogin.dto.Login;
import org.example.shopping.util.exception.enums.ErrorCode;
import org.example.shopping.util.exception.CustomException;
import org.example.shopping.user.service.UserService;
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
    public AuthToken login(@RequestBody @Valid Login loginRequest) {

        return userService.login(loginRequest);
    }
}
