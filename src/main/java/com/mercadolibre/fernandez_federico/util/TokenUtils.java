package com.mercadolibre.fernandez_federico.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import static com.mercadolibre.fernandez_federico.util.SecurityConstants.KEY;

@Component
public class TokenUtils {
    public Claims getAllClaimsFromToken(String token) {
        Claims claims;

        try {
            claims = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(KEY.getBytes()))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {

            claims = null;
        }
        return claims;
    }
}
