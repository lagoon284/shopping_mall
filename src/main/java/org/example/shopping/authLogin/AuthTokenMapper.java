package org.example.shopping.authLogin;

public interface  AuthTokenMapper {

    int insertToken(String userId, String accToken, String refToken);

    int updToken(String userId, String accToken, String refToken);

    AuthToken getToken(String token);

    boolean getTokenToId(String userId);
}
