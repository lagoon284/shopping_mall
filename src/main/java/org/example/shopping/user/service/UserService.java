package org.example.shopping.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shopping.authLogin.dto.AuthToken;
import org.example.shopping.authLogin.dto.Login;
import org.example.shopping.authLogin.dto.LoginInfo;
import org.example.shopping.authLogin.mapper.AuthTokenMapper;
import org.example.shopping.user.dto.User;
import org.example.shopping.user.dto.UserDormencyReq;
import org.example.shopping.user.dto.UserInsertReq;
import org.example.shopping.user.dto.UserUpdateReq;
import org.example.shopping.user.mapper.UserMapper;
import org.example.shopping.util.common.JwtUtil;
import org.example.shopping.util.exception.CustomException;
import org.example.shopping.util.exception.enums.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor        // final이나 @NonNull으로 선언된 필드만을 파라미터로 받는 생성자를 생성.
@Slf4j
public class UserService {

    private final UserMapper userMapper;
    private final AuthTokenMapper authTokenMapper;
    private final JwtUtil jwtUtil;

    public AuthToken login(Login loginReq) {
        // 받은 id 값으로 조회.
        User user = userMapper.selectUserById(loginReq.getUserId());

        LoginInfo loginInfo = authTokenMapper.getLoginInfo(loginReq.getUserId());

        // 조회한 pw 값으로 밉력받은 pw 와 비교하여 검증.
        if (user != null && user.getPw().equals(loginReq.getPw())) {
            // 검증에 이상없으면 계속 진행 ㄱㄱ.
            String jwtAccToken = jwtUtil.generateAccToken(loginInfo);
            String jwtRefToken = jwtUtil.generateRefToken(loginInfo.getId());

            if (authTokenMapper.getTokenToId(user.getId())) {
                if (authTokenMapper.updToken(user.getId(), jwtAccToken, jwtRefToken) != 1) {
                    throw new CustomException(ErrorCode.AUTH_REF_SIGNATURE_UPDATE_ERROR);
                };
            } else {
                if (authTokenMapper.insertToken(user.getId(), jwtAccToken, jwtRefToken) != 1) {
                    throw new CustomException(ErrorCode.AUTH_REF_SIGNATURE_INSERT_ERROR);
                };
            }

            AuthToken token = new AuthToken();

            token.setUserId(loginReq.getUserId());
            token.setAccessToken("seokhoAccAuth " + jwtAccToken);
            token.setRefreshToken("seokhoRefAuth " + jwtRefToken);

            return token;
        } else {
            // 검증 실패시 null 리턴, 컨트롤러에서 핸들링 함.
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
    }

    public AuthToken refLogin(User user) {

        LoginInfo loginInfo = authTokenMapper.getLoginInfo(user.getId());

        String newAccToken = jwtUtil.generateAccToken(loginInfo);
        String newRefToken = jwtUtil.generateRefToken(loginInfo.getId());

        authTokenMapper.updToken(loginInfo.getId(), newAccToken, newRefToken);

        return authTokenMapper.getToken(newRefToken);
    }

    public User getAuthInfo(String token) {

        AuthToken getAuthInfo = authTokenMapper.getToken(token);

        if (getAuthInfo == null || !getAuthInfo.getRefreshToken().equals(token)) {
            // 인증에 실패했을 때 핸들링.
            throw new CustomException(ErrorCode.AUTH_REF_SIGNATURE_FAIL_ERROR);
        }

        return oneUserSelect(getAuthInfo.getUserId());
    }

    public void signupUser(UserInsertReq user) {

        userMapper.insertUser(user);
    }

    public User oneUserSelect(String id) {

        User userInfo = userMapper.selectUserById(id);

        // pw 값 가리기
        // 자리수를 알려주면 안될거 같음...그냥 고정 자리로 해야겠음.
//        String pwHide = String.valueOf('*').repeat(userInfo.getPw().length());
        if (userInfo != null) {
            // 13개
            userInfo.setPw("*************");
        }


        return userInfo;
    }

    public List<User> allUserSelect() {

        List<User> userInfos = userMapper.selectAllUsers();

        if (userInfos != null) {
            // pw 값 가리기
            for (User userInfo : userInfos) {
                // 자리수를 알려주면 안될거 같음...그냥 고정 자리로 해야겠음.
    //            String pwHide = String.valueOf('*').repeat(userInfo.getPw().length());

                // 13개
                userInfo.setPw("*************");
            }
        }

        return userInfos;
    }

    public void updateUserInfo(UserUpdateReq user) {

        userMapper.updateUserInfo(user);
    }

    public void goToSleep(UserDormencyReq user) {

        userMapper.dormencyFlag(user.getId());
    }
}
