package org.example.shopping.mapper;

import org.example.shopping.model.common.AuthToken;

public interface  AuthTokenMapper {

    int insertToken(String userId, String accToken, String refToken);

    int updToken(String userId, String accToken, String refToken);

    AuthToken getToken(String token);

    boolean getTokenToId(String userId);
}
