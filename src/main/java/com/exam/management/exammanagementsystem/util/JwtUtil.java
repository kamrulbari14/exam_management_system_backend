package com.exam.management.exammanagementsystem.util;

import com.exam.management.exammanagementsystem.dto.UserPrinciple;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtUtil {
    private String secretKey = "SecRet";
    private Long expireHour = Long.valueOf("20");

    public String generateToken(Authentication authentication, HttpServletRequest request) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Date now = new Date();
        return Jwts.builder().setId(UUID.randomUUID().toString())
                .claim("username", userPrinciple.getUsername())
                .claim("ip", request.getRemoteAddr())
                .claim("role", userPrinciple.getAuthorities())
                .setSubject(String.valueOf(userPrinciple.getId()))
                .setIssuedAt(now).setExpiration(DateUtils.getExpirationTime(expireHour))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public String generateToken(String userName, HttpServletRequest request) {
        Date now = new Date();
        return Jwts.builder().setId(UUID.randomUUID().toString())
                .claim("username", userName)
                .claim("ip", request.getRemoteAddr())
                .setIssuedAt(now).setExpiration(DateUtils.getExpirationTime(expireHour))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = getClaims(token);
        return Long.valueOf(claims.getSubject());
    }

    public String extractUsername(String token) {
        Claims claims = getClaims(token);
        return (String) claims.get("username");
    }

    public String extractIP(String token) {
        Claims claims = getClaims(token);
        return (String) claims.get("ip");
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public Boolean isValidateToken(String token, HttpServletRequest request) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            if (request.getRemoteAddr().isEmpty() || !request.getRemoteAddr().equals(extractIP(token))) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
