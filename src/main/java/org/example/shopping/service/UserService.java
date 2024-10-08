package org.example.shopping.service;

import jakarta.transaction.Transactional;
import org.example.shopping.mapper.UserMapper;
import org.example.shopping.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void signupUser(User user) {
        userMapper.insertUser(user);
    }

    public User oneUserSelect(String id) {
        return userMapper.selectUserById(id);
    }

    public List<User> allUserSelect() {
        return userMapper.selectAllUsers();
    }

    public void updateUserInfo(User user) {
        userMapper.updateUserInfo(user);
    }

    public void goToSleep(String id) {
        userMapper.dormencyFrag(id);
    }
}
