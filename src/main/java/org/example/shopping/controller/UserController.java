package org.example.shopping.controller;


import lombok.RequiredArgsConstructor;
import org.example.shopping.enums.ErrorCode;
import org.example.shopping.exception.CustomException;
import org.example.shopping.dto.User;
import org.example.shopping.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    // 회원가입.
    @PostMapping("/signup")
    public String signup(@RequestBody User user) {

        int retVal = userService.signupUser(user);

        if (retVal != 1) {
            throw new CustomException(ErrorCode.INSERT_FAIL_USER_ERROR);
        }

        return "Success";
    }

    // id 와 일치하는 계정 정보 하나 select.
    @GetMapping("/findID/{id}")
    public User oneUserSelect(@PathVariable String id) {

        User userInfo = userService.oneUserSelect(id);

        if (userInfo == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return userInfo;
    }

    // 모든 계정 정보 select.
    @GetMapping("/allUserSelect")
    public List<User> allUserSelect() {

        List<User> userInfos = userService.allUserSelect();

        if (userInfos.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_READY_SERVICE_ERROR);
        }

        return userInfos;
    }

    // 회원정보 수정 id 값 변경 불가.
    @PutMapping("/updateInfo")
    public String updateUserInfo(@RequestBody User user) {

        int updResult = userService.updateUserInfo(user);
        
        if (updResult != 1) {
            throw new CustomException(ErrorCode.CONFLICT_REQUEST_USER);
        }

        return "Success";

    }

    // 휴면회원 토글 sleepFrag 값 변경.
    // 회원 수정에 같이 넣어서 처리하는 방법도 있는데 따로 뺀 이유는 혹시 몰라서...
    @PutMapping("/dormency")
    public String goToSleep(@RequestBody User user) {

        int updResult = userService.goToSleep(user);
        
        if (updResult != 1) {
            throw new CustomException(ErrorCode.CONFLICT_REQUEST_DORMENCY);
        }
        
        return "Success";
    }
}
