package com.localaihub.aistar.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类，用于生成和验证 JWT 令牌
 *
 * @apiNote 提供了生成、解析和验证 JWT 的方法
 * @version 0.1.0-SNAPSHOT
 * @date 2024/7/4 00:43
 */
@Component
public class JwtTokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    // 生成JWT令牌
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret));

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        logger.debug("生成的JWT令牌: {}", token);

        return token;
    }

    // 从JWT令牌中获取声明
    public Claims getClaimsFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret));

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        logger.debug("从JWT令牌中提取的声明: {}", claims);

        return claims;
    }

    // 验证JWT令牌
    public boolean validateToken(String token, String username) {
        final String extractedUsername = getUsernameFromToken(token);
        boolean isValid = (extractedUsername.equals(username) && !isTokenExpired(token));

        logger.debug("验证的JWT令牌: {} 是否有效: {}", token, isValid);

        return isValid;
    }

    // 从JWT令牌中获取用户名
    public String getUsernameFromToken(String token) {
        String username = getClaimsFromToken(token).getSubject();

        logger.debug("从JWT令牌中提取的用户名: {}", username);

        return username;
    }

    // 检查JWT令牌是否过期
    public boolean isTokenExpired(String token) {
        final Date expiration = getClaimsFromToken(token).getExpiration();
        boolean isExpired = expiration.before(new Date());

        logger.debug("检查JWT令牌是否过期: {} 是否过期: {}", token, isExpired);

        return isExpired;
    }
}
