package org.example.shopping.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.shopping.model.User;

import java.util.List;

@Mapper
public interface UserMapper {

    // 유저정보 등록
    void insertUser(User user);

    // id 와 일치하는 유저정보 1개 select
    User selectUserById(@Param("id") String id);

    // 모든 유저 정보 select
    List<User> selectAllUsers();

    // id 와 일치하는 유저정보 수정 (id는 바꿀 수 없음.)
    void updateUserInfo(User user);

    // id 와 일치하는 유저의 sleepFrag 토글
    void dormencyFrag(String id);
}
