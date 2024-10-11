package org.example.shopping.controller;

import org.example.shopping.model.api.ApiRes;
import org.example.shopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiRes<String>> login(@RequestBody Map<String, String> loginRequest) {
        String token = userService.login(loginRequest.get("userId"), loginRequest.get("pw"));

        if (token == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ApiRes.diyResult("ID에 해당하는 계정정보가 없습니다.", null));
        }

        return ResponseEntity.ok(ApiRes.diyResult("로그인에 성공하였습니다.", token));
    }
}
