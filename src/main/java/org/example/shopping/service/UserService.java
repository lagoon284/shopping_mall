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
        User user = userMapper.selectUserById(userId);

        if (user != null && user.getPw().equals(password)) {
            return jwtUtil.generateToken(user);
        } else {
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
