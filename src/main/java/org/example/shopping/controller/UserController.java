package org.example.shopping.controller;


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
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 회원가입.
    @PostMapping("/signup")
    public ResponseEntity<ApiRes<String>> signup(@RequestBody User user) {

        user.setRegDate(TimeConverter.toDayToString());
        user.setAddr("대한민국 그 어딘가에 있음");

        int insertResult = userService.signupUser(user);

        if (insertResult != 1) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiRes.diyResult("USER 테이블 INSERT 작업에 이상이 있습니다.", null));
        }

        return ResponseEntity
                .ok(ApiRes.diyResult("USER 테이블에 정상적으로 INSERT 되었습니다.", null));
    }

    // id 와 일치하는 계정 정보 하나 select.
    @GetMapping("/findID/{id}")
    public ResponseEntity<ApiRes<User>> oneUserSelect(@PathVariable String id) {

        User userInfo = userService.oneUserSelect(id);

        if (userInfo == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiRes.diyResult("SELECT 작업 중 오류가 발생했습니다.", null));
        }

        return ResponseEntity
                .ok(ApiRes.diyResult("성공", userInfo));
    }

    // 모든 계정 정보 select.
    @GetMapping("/allUserSelect")
    public ResponseEntity<ApiRes<List<User>>> allUserSelect() {

        List<User> usersInfo = userService.allUserSelect();

        if (usersInfo == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiRes.diyResult("TABLE 에 데이터가 없습니다.", null));
        }

        return ResponseEntity
                .ok(ApiRes.diyResult("성공", usersInfo));
    }

    // 회원정보 수정 id 값 변경 불가.
    @PostMapping("/updateInfo")
    public ResponseEntity<ApiRes<String>> updateUserInfo(@RequestBody User user) {

        user.setUpdDate(TimeConverter.toDayToString());

        int updResult = userService.updateUserInfo(user);
        
        if (updResult != 1) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiRes.diyResult("단건 UPDATE에 실패하였습니다.", null));
        }

        return ResponseEntity
                .ok(ApiRes.diyResult("업데이트 성공", null));

    }

    // 휴면회원 토글 sleepFrag 값 변경.
    // 회원 수정에 같이 넣어서 처리하는 방법도 있는데 따로 뺀 이유는 혹시 몰라서...
    @PostMapping("/dormency")
    public ResponseEntity<ApiRes<String>> goToSleep(@RequestBody User user) {

        user.setUpdDate(TimeConverter.toDayToString());

        int updResult = userService.goToSleep(user.getId());
        
        if (updResult != 1) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiRes.diyResult("휴면계정 전환 실패", null));
        }
        
        return ResponseEntity
                .ok(ApiRes.diyResult("휴면계정 전환 성공", null));
    }
}
