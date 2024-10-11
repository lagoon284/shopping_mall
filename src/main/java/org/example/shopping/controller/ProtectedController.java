package org.example.shopping.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.http.HttpServletResponse;
import org.example.shopping.model.User;
import org.example.shopping.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/protected")
public class ProtectedController {

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/data")
    public ResponseEntity<String> getProtectedData(@RequestHeader("Authorization") String token, HttpServletResponse headers) {
        try {
            if (token != null && token.startsWith("seokhoAuth ")) {
                String jwt = token.substring(11);
                if (!jwtUtil.isTokenExpired(jwt)) {
                    User user = jwtUtil.extractUserObj(jwt);
                    return ResponseEntity.ok("Protected data for " + user.toString());
                }
            }
        } catch (SignatureException e) {
            // 인증에 실패했을 때 핸들링.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization 값이 다릅니다.");
        } catch (ExpiredJwtException e) {
            // 유요기간이 지났을 때 핸들링.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("다시 로그인 해주세요.");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
    }
}
