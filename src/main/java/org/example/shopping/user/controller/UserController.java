package org.example.shopping.user.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.shopping.user.dto.User;
import org.example.shopping.user.dto.UserDormencyReq;
import org.example.shopping.user.dto.UserInsertReq;
import org.example.shopping.user.dto.UserUpdateReq;
import org.example.shopping.user.service.UserService;
import org.example.shopping.util.common.ValidationUtil;
import org.example.shopping.util.exception.enums.ErrorCode;
import org.example.shopping.util.exception.CustomException;
import org.example.shopping.util.common.TimeConverter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    // 회원가입.
    @PostMapping("/signup")
    public String signup(@RequestBody @Valid UserInsertReq user) {

        userService.signupUser(user);

        return "Success";
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
    @PutMapping("/updateInfo")
    public String updateUserInfo(@RequestBody @Valid UserUpdateReq user) {

        userService.updateUserInfo(user);

        return "Success";
    }

    // 휴면회원 토글 sleepFrag 값 변경.
    // 회원 수정에 같이 넣어서 처리하는 방법도 있는데 따로 뺀 이유는 혹시 몰라서...
    @PutMapping("/dormency")
    public String goToSleep(@RequestBody @Valid UserDormencyReq user) {

        userService.goToSleep(user);
        
        return "Success";
    }
}
