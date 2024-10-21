package org.example.shopping.controller;

import lombok.RequiredArgsConstructor;
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
    public String login(@RequestBody Map<String, String> loginRequest) {

        Map<String, String> token = userService.login(loginRequest.get("userId"), loginRequest.get("pw"));

        if (token == null || token.isEmpty()) {
            return "ID에 해당하는 계정정보가 없습니다.";
        }

        return "로그인에 성공하였습니다." + token;
    }
}
