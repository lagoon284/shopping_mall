package org.example.shopping.util.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.example.shopping.authLogin.dto.LoginInfo;
import org.example.shopping.util.exception.dto.CustomException;
import org.example.shopping.util.exception.enums.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private String secret = "lunchfairy";
//    private Long expiration = 12000L*60*60;  // 대충 12시간.
    private Long expiration = 120000L;  // 대충 2분 test 용.

    // 로그인 성공 여부 id 값으로 select 했을 때 계정 정보가 있으면 jwt에 유저 정보를 담음.
    public String generateAccToken(LoginInfo user) {

        ObjectMapper obj = new ObjectMapper();

        String userJson;

        try {
            // user 객체를 String 타입으로 변환해줌.
            userJson = obj.writeValueAsString(user);
        } catch(Exception e) {
            throw new RuntimeException("User object to JSON conversion failed", e);
        }

        return Jwts.builder()
                // jwt에 정보를 set 해줌.
                .setSubject(userJson)
                // 토큰의 생명 시간...? 유효기간.
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                // 시그니처의 암호문과 암호화 설정.
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String generateRefToken(String userId) {

        return Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + (expiration * 20)))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

//    public String extractUserName(String token) {
//        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
//    }

    // String 인 토큰의 payLoad 값을 모델로 변환해줌.
    public LoginInfo extractUserObj(String token) {

        ObjectMapper obj = new ObjectMapper();

        try {
            // jwt 분해해서 String으로 변환.
            String userJson = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            // objectMapper로 객체로 변환 시켜서 리턴.
            return obj.readValue(userJson, LoginInfo.class);

        } catch (SignatureException e) {
            // 인증에 실패했을 때 핸들링.
            throw new CustomException(ErrorCode.AUTH_SIGNATURE_FAIL_ERROR);
        } catch (Exception e) {
            throw new RuntimeException("User object to JSON conversion failed", e);
        }
    }

//    public Claims extractAuth(String token) {
//        return Jwts.parser()
//                .setSigningKey(secret)
//                .parseClaimsJws(token)
//                .getBody();
//    }

    // 토큰이 아직 유효기간이 남았는지 체크. 지나면 로그인 안됨.
    // 토큰이 유효하면 true, 만료되었으면 false.
    public boolean isTokenExpired(String token) {
        try {
           return   !Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token)
                        .getBody()
                        .getExpiration()
                        .before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
