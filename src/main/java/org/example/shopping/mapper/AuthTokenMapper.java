package org.example.shopping.mapper;

import org.example.shopping.model.AuthToken;
import org.example.shopping.model.User;

public interface AuthTokenMapper {

    int insertToken(String userId, String accToken, String refToken);

    AuthToken getToken(String token);
}
