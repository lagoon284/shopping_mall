package org.example.shopping.util.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.example.shopping.authLogin.dto.LoginInfo;
import org.example.shopping.util.Role;
import org.example.shopping.util.exception.dto.CustomException;
import org.example.shopping.util.exception.enums.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    // 추후에 주입받아 사용할 것.
    @Value("MyL4QBUjILcWWrHjljfMxT2ENPSGeV8jg5fYe0gi4nh+oy8bwHTlx5cs8+9cgvZ3sbDE+gHXsKVkZ24od7jigw==")
    private String secret;

    private SecretKey key;

//    private Long expiration = 12000L*60*60;  // 대충 12시간.
    private Long expiration = 120000L;  // 대충 2분 test 용.

    // @PostConstruct: 의존성 주입이 완료된 후 실행되는 메소드
    @PostConstruct
    public void init() {
        // secret 값을 Base64 인코딩된 문자열로 다룹니다.
        // String을 바이트 배열로 변환한 뒤, 이를 사용해 Key 객체를 생성합니다.
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 로그인 성공 여부 id 값으로 select 했을 때 계정 정보가 있으면 jwt에 유저 정보를 담음.
    public String generateAccToken(String userId, Role role) {

        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("role", role.name());
        // 필요시 이름정도는 추가 할 수 있음.
//        claims.put("name", user.getName());


        return Jwts.builder()
                // jwt에 정보를 set 해줌.
                .setClaims(claims)
                // 토큰의 생명 시간...? 유효기간.
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                // 시그니처의 암호문과 암호화 설정.
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateRefToken(String userId) {

        return Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + (expiration))) /* * 20 */
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

//    public String extractUserName(String token) {
//        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
//    }

    // String 인 토큰의 payLoad 값을 모델로 변환해줌.
    public LoginInfo extractUserObj(String token) {

//        ObjectMapper obj = new ObjectMapper();

        try {
            // jwt 분해해서 String으로 변환.
            String userJson = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            // objectMapper로 객체로 변환 시켜서 리턴.
            return new LoginInfo(userJson);

        } catch (SignatureException e) {
            // 인증에 실패했을 때 핸들링.
            throw new CustomException(ErrorCode.AUTH_SIGNATURE_FAIL_ERROR);
        } catch (Exception e) {
            throw new RuntimeException("User object to JSON conversion failed", e);
        }
    }

    /**
     * 토큰에서 Claims(정보)를 추출하는 메소드
     * @param token 정보를 추출할 토큰
     * @return 토큰의 Claims 객체
     */
    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // 서명 키 설정
                .build()
                .parseClaimsJws(token) // 토큰 파싱 및 검증
                .getBody(); // Claims 부분 반환
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
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return false;
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
            return true;
        } catch (Exception e) {
            log.error("Invalid Token, 만료 이외의 다른 예외 발생: {}", e.getMessage());
            return false;
        }
    }
}
