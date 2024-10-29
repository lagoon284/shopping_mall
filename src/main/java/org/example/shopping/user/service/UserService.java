package org.example.shopping.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shopping.authLogin.dto.AuthToken;
import org.example.shopping.user.dto.User;
import org.example.shopping.user.dto.UserDormencyReq;
import org.example.shopping.user.dto.UserInsertReq;
import org.example.shopping.user.dto.UserUpdateReq;
import org.example.shopping.user.mapper.UserMapper;
import org.example.shopping.util.common.TimeConverter;
import org.example.shopping.util.exception.enums.ErrorCode;
import org.example.shopping.util.exception.CustomException;
import org.example.shopping.authLogin.mapper.AuthTokenMapper;
import org.example.shopping.util.common.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor        // final이나 @NonNull으로 선언된 필드만을 파라미터로 받는 생성자를 생성.
@Slf4j
public class UserService {

    private final UserMapper userMapper;
    private final AuthTokenMapper authTokenMapper;
    private final JwtUtil jwtUtil;

    public AuthToken login(String userId, String password) {
        // 받은 id 값으로 조회.
        User user = userMapper.selectUserById(userId);

        // 조회한 pw 값으로 밉력받은 pw 와 비교하여 검증.
        if (user != null && user.getPw().equals(password)) {
            // 검증에 이상없으면 계속 진행 ㄱㄱ.
            String jwtAccToken = jwtUtil.generateAccToken(user);
            String jwtRefToken = jwtUtil.generateRefToken(user.getId());

            if (authTokenMapper.getTokenToId(user.getId())) {
                authTokenMapper.updToken(user.getId(), jwtAccToken, jwtRefToken);
            } else {
                authTokenMapper.insertToken(user.getId(), jwtAccToken, jwtRefToken);
            }

            AuthToken token = new AuthToken();

            token.setUserId(userId);
            token.setAccessToken(jwtAccToken);
            token.setRefreshToken(jwtRefToken);

            return token;
        } else {
            // 검증 실패시 null 리턴, 컨트롤러에서 핸들링 함.
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
    }

    public AuthToken refLogin(User user) {

        String newAccToken = jwtUtil.generateAccToken(user);
        String newRefToken = jwtUtil.generateRefToken(user.getId());

        int retVal = authTokenMapper.updToken(user.getId(), newAccToken, newRefToken);

        if (retVal != 1) {
            throw new CustomException(ErrorCode.AUTH_REF_SIGNATURE_UPDATE_ERROR);
        }

        return authTokenMapper.getToken(newRefToken);
    }

    public AuthToken getAuthInfo(String token) {
        return authTokenMapper.getToken(token);
    }

    @Transactional
    public void signupUser(UserInsertReq user) {

        // 등록일시 set.
        user.setRegDate(TimeConverter.toDayToString());

        if (userMapper.insertUser(user) != 1) {
            throw new CustomException(ErrorCode.INSERT_FAIL_USER_ERROR);
        }
    }

    public User oneUserSelect(String id) {

        User userInfo = userMapper.selectUserById(id);

        if (userInfo == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return userInfo;
    }

    public List<User> allUserSelect() {

        List<User> userInfos = userMapper.selectAllUsers();

        if (userInfos.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_READY_SERVICE_ERROR);
        }

        return userInfos;
    }

    public void updateUserInfo(UserUpdateReq user) {

        if (userMapper.updateUserInfo(user) != 1) {
            throw new CustomException(ErrorCode.CONFLICT_REQUEST_USER);
        }
    }

    public void goToSleep(UserDormencyReq user) {

        user.setUpdDate(TimeConverter.toDayToString());

        if (userMapper.dormencyFrag(user.getId()) != 1) {
            throw new CustomException(ErrorCode.CONFLICT_REQUEST_DORMENCY);
        }
    }
}
