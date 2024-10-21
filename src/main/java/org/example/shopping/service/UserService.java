package org.example.shopping.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.shopping.mapper.AuthTokenMapper;
import org.example.shopping.mapper.UserMapper;
import org.example.shopping.model.common.AuthToken;
import org.example.shopping.model.User;
import org.example.shopping.util.JwtUtil;
import org.example.shopping.util.TimeConverter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor        // final이나 @NonNull으로 선언된 필드만을 파라미터로 받는 생성자를 생성.
public class UserService {

    private final UserMapper userMapper;
    private final AuthTokenMapper authTokenMapper;
    private final JwtUtil jwtUtil;

    public Map<String, String> login(String userId, String password) {
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

            Map<String, String> mapToken = new HashMap<>();

            mapToken.put("accessToken", jwtAccToken);
            mapToken.put("refreshToken", jwtRefToken);

            return mapToken;
        } else {
            // 검증 실패시 null 리턴, 컨트롤러에서 핸들링 함.
            return null;
        }
    }

    public AuthToken refLogin(User user) {

        String newAccToken = jwtUtil.generateAccToken(user);
        String newRefToken = jwtUtil.generateRefToken(user.getId());

        int retVal = authTokenMapper.updToken(user.getId(), newAccToken, newRefToken);

        if (retVal != 1) {
            return null;
        }

        return authTokenMapper.getToken(newRefToken);
    }

    public AuthToken getAuthInfo(String token) {
        return authTokenMapper.getToken(token);
    }

    @Transactional
    public int signupUser(User user) {

        // test data set.
        user.setRegDate(TimeConverter.toDayToString());
        user.setAddr("대한민국 그 어딘가에 있음");

        return userMapper.insertUser(user);
    }

    public User oneUserSelect(String id) {
        return userMapper.selectUserById(id);
    }

    public List<User> allUserSelect() {
        return userMapper.selectAllUsers();
    }

    public int updateUserInfo(User user) {

        user.setUpdDate(TimeConverter.toDayToString());

        return userMapper.updateUserInfo(user);
    }

    public int goToSleep(User user) {

        user.setUpdDate(TimeConverter.toDayToString());

        return userMapper.dormencyFrag(user.getId());
    }
}
