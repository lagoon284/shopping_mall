package org.example.shopping.authLogin.mapper;

import org.example.shopping.authLogin.dto.AuthToken;
import org.example.shopping.authLogin.dto.LoginInfo;

public interface  AuthTokenMapper {

    int insertToken(String userId, String accToken, String refToken);

    int updToken(String userId, String accToken, String refToken);

    AuthToken getTokenByRefToken(String token);

    AuthToken getTokenByAccToken(String token);

    boolean getTokenToId(String userId);

    LoginInfo getLoginInfo(String userId);
}
