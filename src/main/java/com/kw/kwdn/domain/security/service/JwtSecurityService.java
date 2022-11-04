package com.kw.kwdn.domain.security.service;


import com.kw.kwdn.global.error.exception.JwtAccessDeniedException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtSecurityService {
    private String JWT_SECRET = "hello world";

    public String createToken(String subject, long expireTime) {
        if (expireTime <= 0) throw new IllegalArgumentException("초기화하는 시간이 0보다 작으면 안됩니다.");
        SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;

        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(JWT_SECRET);
        Key signingKey = new SecretKeySpec(secretKeyBytes, algorithm.getJcaName());

        return Jwts.builder()
                .setSubject(subject)
                .signWith(algorithm, signingKey)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .compact();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(DatatypeConverter
                            .parseBase64Binary(JWT_SECRET))
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

    public String getSubject(String token) {
        if (!isValid(token)) return null;
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(JWT_SECRET))
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
