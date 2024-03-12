package com.demo.tlias.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;

/**
 * @author wjh
 * @date 2024/3/3 19:16
 */
public class JwtUtils {
    private static String signKey = "daydream";
    private static Long expire = 43200000L;

    public static String genJwt(HashMap<String, Object> claims) {
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "daydream")
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                .compact();
        return jwt;
    }

    public static Claims parseJwt(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }
}
