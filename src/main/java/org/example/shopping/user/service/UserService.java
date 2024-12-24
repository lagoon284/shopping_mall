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
        // 받은 id 값으로 조회. 전체 데이터. pw포함.
        User user = userMapper.selectUserById(loginReq.getUserId());

        if (user == null) {
            // 검증 실패시 null 리턴, 컨트롤러에서 핸들링 함.
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        // 일부 데이터 userNo, id, name
        LoginInfo loginInfo = new LoginInfo(user.getUserNo(), user.getId(), user.getName());

        // 조회한 pw 값으로 밉력받은 pw 와 비교하여 검증.
        if (user.getPw().equals(loginReq.getPw())) {

            String jwtAccToken = jwtUtil.generateAccToken(loginInfo);
            String jwtRefToken = jwtUtil.generateRefToken(loginInfo.getId());

            // 입력된 아이디 회원 정보에 AUTHTOKEN이 존재하는지?
            if (authTokenMapper.getTokenToId(user.getId())) {
                // 존재하면 업데이트.
                if (authTokenMapper.updToken(user.getId(), jwtAccToken, jwtRefToken) != 1) {
                    throw new CustomException(ErrorCode.AUTH_REF_SIGNATURE_UPDATE_ERROR);
                };
            } else {
                // 존재하지 않으면 인서트.
                if (authTokenMapper.insertToken(user.getId(), jwtAccToken, jwtRefToken) != 1) {
                    throw new CustomException(ErrorCode.AUTH_REF_SIGNATURE_INSERT_ERROR);
                };
            }

            AuthToken token = new AuthToken();

            token.setUserId(loginReq.getUserId());
            token.setAccessToken(jwtAccToken);
            token.setRefreshToken(jwtRefToken);

            return token;
        } else {
            // 검증 실패시 null 리턴, 컨트롤러에서 핸들링 함.
            throw new CustomException(ErrorCode.USER_NOT_EQUALS_PASSWORD);
        }
    }

    public AuthToken refLogin(User user) {

        AuthToken retToken = new AuthToken();
        LoginInfo loginInfo = authTokenMapper.getLoginInfo(user.getId());

        String newAccToken = jwtUtil.generateAccToken(loginInfo);
        String newRefToken = jwtUtil.generateRefToken(loginInfo.getId());

        retToken.setUserId(user.getId());
        retToken.setAccessToken(newAccToken);
        retToken.setRefreshToken(newRefToken);

        authTokenMapper.updToken(loginInfo.getId(), newAccToken, newRefToken);

        return retToken;
    }


//  클라이언트가 '무언가를 할때' auth token을 갱신하도록 하는 메소드. 프론트에서 책임지는게 맞는거 같아서 주석처리.
//    public AuthToken checkAuth(String token) {
//
//        AuthToken authToken = null;
//
//        if (token != null && token.startsWith("seokhoAccAuth ")) {
//            String jwt = token.substring(14);
//
//            // acc token 유효기간 지났을 때,
//            if (!jwtUtil.isTokenExpired(jwt)) {
//                AuthToken authTokenData = authTokenMapper.getToken(jwt);
//
//                if (authTokenData == null || authTokenData.getRefreshToken() == null || authTokenData.getRefreshToken().isEmpty()) {
//                    throw new CustomException(ErrorCode.AUTH_SIGNATURE_EXPIRED_ERROR);
//                }
//
//                String getRefToken = authTokenData.getRefreshToken();
//
//                // ref token 유효기간 확인.
//                if (!jwtUtil.isTokenExpired(getRefToken)) {
//                    throw new CustomException(ErrorCode.AUTH_REF_SIGNATURE_EXPIRED_ERROR);
//                }
//
//                // ref token 유효기간이 유효할 때 token 갱신.
//                authToken = refLogin(getAuthInfo(jwt));
//            } else {
//                // 유효기간이 지났을 때 핸들링.
//                throw new CustomException(ErrorCode.AUTH_SIGNATURE_EXPIRED_ERROR);
//            }
//        } else {
//            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
//        }
//
//        return authToken;
//    }

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
