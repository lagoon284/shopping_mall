package org.example.shopping.controller;


import org.example.shopping.model.User;
import org.example.shopping.service.UserService;
import org.example.shopping.util.TimeConverter;
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

        user.setRegDate(TimeConverter.toDayToString());

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

        user.setUpdDate(TimeConverter.toDayToString());

        userService.updateUserInfo(user);
    }

    // 휴면회원 토글 sleepFrag 값 변경.
    // 회원 수정에 같이 넣어서 처리하는 방법도 있는데 따로 뺀 이유는 혹시 몰라서...
    @PostMapping("/dormency")
    public void goToSleep(@RequestBody User user) {

        user.setUpdDate(TimeConverter.toDayToString());

        userService.goToSleep(user.getId());
    }
}
