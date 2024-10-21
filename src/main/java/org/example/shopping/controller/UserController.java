package org.example.shopping.controller;


import lombok.RequiredArgsConstructor;
import org.example.shopping.model.User;
import org.example.shopping.service.UserService;
import org.example.shopping.model.api.ApiRes;
import org.example.shopping.util.TimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            return "USER 테이블 INSERT 작업에 이상이 있습니다." + retVal;
        }

        return "USER 테이블에 정상적으로 INSERT 되었습니다. => " + retVal;
    }

    // id 와 일치하는 계정 정보 하나 select.
    @GetMapping("/findID/{id}")
    public ResponseEntity<User> oneUserSelect(@PathVariable String id) {

        return ResponseEntity.ok(userService.oneUserSelect(id));
    }

    // 모든 계정 정보 select.
    @GetMapping("/allUserSelect")
    public ResponseEntity<List<User>> allUserSelect() {

        return ResponseEntity.ok(userService.allUserSelect());
    }

    // 회원정보 수정 id 값 변경 불가.
    @PutMapping("/updateInfo")
    public ResponseEntity<String> updateUserInfo(@RequestBody User user) {

        int updResult = userService.updateUserInfo(user);
        
        if (updResult != 1) {
            return ResponseEntity.badRequest().body("단건 UPDATE에 실패하였습니다. => " + updResult);
        }

        return ResponseEntity.ok("업데이트 성공 => " + updResult);

    }

    // 휴면회원 토글 sleepFrag 값 변경.
    // 회원 수정에 같이 넣어서 처리하는 방법도 있는데 따로 뺀 이유는 혹시 몰라서...
    @PutMapping("/dormency")
    public String goToSleep(@RequestBody User user) {

        int updResult = userService.goToSleep(user);
        
        if (updResult != 1) {
            return "휴면계정 전환 실패 => " + updResult;
        }
        
        return "휴면계정 전환 성공 => " + updResult;
    }
}
