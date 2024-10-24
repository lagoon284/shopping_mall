package org.example.shopping.funcBase.user;


import lombok.RequiredArgsConstructor;
import org.example.shopping.util.exception.ErrorCode;
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
    public String signup(@RequestBody User user) {

        // 등록일시 set.
        user.setRegDate(TimeConverter.toDayToString());

        // param 검증 null check.
        try {
            boolean paramCheck = user.getId().isEmpty()
                    || user.getPw().isEmpty()
                    || user.getName().isEmpty()
                    || user.getAddr().isEmpty()
                    || user.getRegDate().isEmpty();

            if (paramCheck) {
                throw new CustomException(ErrorCode.INVALID_PARAMETER);
            }
        } catch (NullPointerException e) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }

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
    public String updateUserInfo(@RequestBody User user) {

        // 수정일시 set.
        user.setUpdDate(TimeConverter.toDayToString());

        // param 검증 null check.
        try {
            boolean paramCheck = user.getId().isEmpty()
                    || user.getPw().isEmpty()
                    || user.getName().isEmpty()
                    || user.getAddr().isEmpty()
                    || user.getUpdDate().isEmpty();

            if (paramCheck) {
                throw new CustomException(ErrorCode.INVALID_PARAMETER);
            }
        } catch (NullPointerException e) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }

        userService.updateUserInfo(user);

        return "Success";

    }

    // 휴면회원 토글 sleepFrag 값 변경.
    // 회원 수정에 같이 넣어서 처리하는 방법도 있는데 따로 뺀 이유는 혹시 몰라서...
    @PutMapping("/dormency")
    public String goToSleep(@RequestBody User user) {

        user.setUpdDate(TimeConverter.toDayToString());

        // param 검증 null check.
        if (user.getId() == null || user.getId().isEmpty() || user.getId().length() > 30) {
            throw new CustomException(ErrorCode.INVALID_PARAMETER);
        }

        userService.goToSleep(user);
        
        return "Success";
    }
}
