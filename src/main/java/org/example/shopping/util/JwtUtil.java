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

    public String generateToken(User user) {

        ObjectMapper obj = new ObjectMapper();
        String userJson;

        try {
            userJson = obj.writeValueAsString(user);
        } catch(Exception e) {
            throw new RuntimeException("User object to JSON conversion failed", e);
        }

        return Jwts.builder()
                .setSubject(userJson)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

//    public String extractUserName(String token) {
//        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
//    }

//    public Object extractUserObj(String token) {
//        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getHeader().get("userInfo");
//    }

    public User extractUserObj(String token) {

        ObjectMapper obj = new ObjectMapper();

        String userJson = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        try {
            return obj.readValue(userJson, User.class);
        } catch(Exception e) {
            throw new RuntimeException("User object to JSON conversion failed", e);
        }
    }

    public boolean isTokenExpired(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }
}
