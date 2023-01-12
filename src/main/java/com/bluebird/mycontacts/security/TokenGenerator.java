package com.bluebird.mycontacts.security;

import com.bluebird.mycontacts.extra.SecurityVariables;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenGenerator {

    public String generateToken(Authentication authentication) {
        final String phone = authentication.getName();

        final Date date = new Date();
        final Date expireDate = new Date(date.getTime() + SecurityVariables.VALIDATE);

        return Jwts.builder()
                .setSubject(phone)
                .setIssuedAt(date)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, SecurityVariables.SECRET)
                .compact();
    }

    public String getUsernameFromToken(String token){
        final Claims claims = Jwts.parser().setSigningKey(SecurityVariables.SECRET).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            final Claims claims = Jwts.parser().setSigningKey(SecurityVariables.SECRET).parseClaimsJws(token).getBody();
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public String getTokenFromRequest(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if (header.startsWith("Bearer ")){
            return header.substring(7);
        }
        return null;
    }
}
