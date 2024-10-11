package org.example.shopping.service;

import jakarta.transaction.Transactional;
import org.example.shopping.mapper.UserMapper;
import org.example.shopping.model.User;
import org.example.shopping.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    public String login(String userId, String password) {
        // 받은 id 값으로 조회.
        User user = userMapper.selectUserById(userId);

        // 조회한 pw 값으로 밉력받은 pw 와 비교하여 검증.
        if (user != null && user.getPw().equals(password)) {
            // 검증에 이상없으면 계속 진행 ㄱㄱ.
            return jwtUtil.generateToken(user);
        } else {
            // 검증 실패시 null 리턴, 컨트롤러에서 핸들링 함.
            return null;
        }
    }

    @Transactional
    public int signupUser(User user) {
        return userMapper.insertUser(user);
    }

    public User oneUserSelect(String id) {
        return userMapper.selectUserById(id);
    }

    public List<User> allUserSelect() {
        return userMapper.selectAllUsers();
    }

    public int updateUserInfo(User user) {
        return userMapper.updateUserInfo(user);
    }

    public int goToSleep(String id) {
        return userMapper.dormencyFrag(id);
    }
}
