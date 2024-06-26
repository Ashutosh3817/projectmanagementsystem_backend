//package com.pms.config;
//
//import java.util.Collection;
//import java.util.Date;
//
//import javax.crypto.SecretKey;
//
//import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//
////we use 
//
//public class JwtProvider {
//	
//	static SecretKey key = Keys.hmacShaKeyFor(com.pms.config.JwtConstant.SECRET_KEY.getBytes());
////	String jwt ="";
//	//method for generate token
//	public static String generateToken(Authentication auth) {
////		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
////		
////		//get the role insider the authorities
//		//code for direct generating token
//		//we write the issue data=>current data and expiration date=>after 24hrs for the token
//		//also set claims
//
//		String jwt = Jwts.builder().setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime()+86400000)).claim("email",auth.getName())
//				.signWith(key).compact();
//		
//		return jwt;
//
//	}
//	public static String getEmailFromToken(String jwt) {
//		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
//		
//	    String email=String.valueOf(claims.get("email"));
//		//first we get the claims from the jwt and then fetch the email from the claims
//	    
//	    return email;
//	}
//}
package com.pms.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component 
public class JwtProvider {
    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    public static String generateToken(Authentication authentication) {
        return Jwts.builder()
            .setSubject(authentication.getName())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(secretKey)
            .compact();
    }
}

