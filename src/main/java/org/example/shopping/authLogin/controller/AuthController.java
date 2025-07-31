package org.example.shopping.authLogin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shopping.authLogin.dto.Login;
import org.example.shopping.user.service.UserService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    // 로그인 하는 컨트롤러... service는 userService를 사용함....
    @PutMapping("/login")
    public String login(@RequestBody @Valid Login loginRequest) {

        return userService.login(loginRequest).getAccessToken();
    }
}
