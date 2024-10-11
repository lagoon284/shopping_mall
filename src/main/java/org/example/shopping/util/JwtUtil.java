package org.example.shopping.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.shopping.model.User;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private String secret = "lunchfairy";
    private Long expiration = 12000L*60*60;  // 대충 12시간.
//    private Long expiration = 20000L;  // 대충 20초

    // 로그인 성공 여부 id 값으로 select 했을 때 계정 정보가 있으면 jwt에 유저 정보를 담음.
    public String generateToken(User user) {

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

//    public String extractUserName(String token) {
//        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
//    }

    public User extractUserObj(String token) {

        ObjectMapper obj = new ObjectMapper();

        // jwt 분해해서 String으로 변환.
        String userJson = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        try {
            // objectMapper로 객체로 변환 시켜서 리턴.
            return obj.readValue(userJson, User.class);
        } catch(Exception e) {
            throw new RuntimeException("User object to JSON conversion failed", e);
        }
    }

    // 토큰이 아직 유효기간이 남았는지 체크. 지나면 로그인 안됨.
    public boolean isTokenExpired(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }
}
