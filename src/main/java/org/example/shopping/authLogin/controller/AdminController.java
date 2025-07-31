package org.example.shopping.authLogin.controller;


import lombok.RequiredArgsConstructor;
import org.example.shopping.user.dto.User;
import org.example.shopping.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    // 모든 계정 정보 select.
    @GetMapping("/user/allUserSelect")
    public List<User> allUserSelect() {

        return userService.allUserSelect();
    }
}
