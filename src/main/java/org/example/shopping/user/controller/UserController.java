package org.example.shopping.user.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shopping.user.dto.User;
import org.example.shopping.user.dto.UserDormencyReq;
import org.example.shopping.user.dto.UserInsertReq;
import org.example.shopping.user.dto.UserUpdateReq;
import org.example.shopping.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    // 회원가입.
    @PostMapping("/signup")
    public void signup(@RequestBody @Valid UserInsertReq user) {

        userService.signupUser(user);
    }

    // id 와 일치하는 계정 정보 하나 select.
    @GetMapping("/{id}")
    public User oneUserSelect(@PathVariable String id) {

        return userService.oneUserSelect(id);
    }



    // 회원정보 수정 id 값 변경 불가.
    @PutMapping("/updateInfo")
    public void updateUserInfo(@RequestHeader("Authorization") String token, @RequestBody @Valid UserUpdateReq user) {

        userService.updateUserInfo(user);
    }

    // 휴면회원 토글 sleepFlag 값 변경.
    // 회원 수정에 같이 넣어서 처리하는 방법도 있는데 따로 뺀 이유는 혹시 몰라서...
    @PutMapping("/dormency")
    public void goToSleep(@RequestBody @Valid UserDormencyReq user) {

        userService.goToSleep(user);
    }
}
