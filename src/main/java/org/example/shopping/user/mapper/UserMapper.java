package org.example.shopping.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.shopping.user.dto.User;
import org.example.shopping.user.dto.UserInsertReq;
import org.example.shopping.user.dto.UserUpdateReq;

import java.util.List;

@Mapper
public interface UserMapper {

    // 유저정보 등록
    int insertUser(UserInsertReq user);

    // id 와 일치하는 유저정보 1개 select
    User selectUserById(@Param("id") String id);

    User loginUserById(String id, String pw);

    // 모든 유저 정보 select
    List<User> selectAllUsers();

    // id 와 일치하는 유저정보 수정 (id는 바꿀 수 없음.)
    int updateUserInfo(UserUpdateReq user);

    // id 와 일치하는 유저의 sleepFrag 토글
    int dormencyFrag(String id);
}
