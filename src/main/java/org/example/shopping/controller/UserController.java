package org.example.shopping.controller;


import org.example.shopping.model.User;
import org.example.shopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 회원가입.
    @PostMapping("/signup")
    public void signup(@RequestBody User user) {
        userService.signupUser(user);
    }

    // id 와 일치하는 계정 정보 하나 select.
    @GetMapping("/findID/{id}")
    public User oneUserSelect(@PathVariable String id) {
        return userService.oneUserSelect(id);
    }

    // 모든 계정 정보 select.
    @GetMapping("/allUserSelect")
    public List<User> allUserSelect() {
        return userService.allUserSelect();
    }

    // 회원정보 수정 id 값 변경 불가.
    @PostMapping("/updateInfo")
    public void updateUserInfo(@RequestBody User user) {
        userService.updateUserInfo(user);
    }

    // 휴면회원 토글 sleepFrag 값 변경.
    @PostMapping("/dormency")
    public void goToSleep(@RequestBody User user) {
        userService.goToSleep(user.getId());
    }
}
