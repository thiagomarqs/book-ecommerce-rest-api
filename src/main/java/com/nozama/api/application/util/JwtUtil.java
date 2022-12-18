package com.nozama.api.application.util;

import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {

  @Value("${security.jwt.secret}")
  private String secret;

  public static Boolean isAuthorizationHeaderValid(String authorizationHeaderValue) {
    
    if(authorizationHeaderValue == null) return false;

    var isBlank = authorizationHeaderValue.isBlank();
    var containsBearer = authorizationHeaderValue.contains("Bearer ");

    if(isBlank || !containsBearer) return false;

    var splitted = authorizationHeaderValue.split(" ");

    return splitted[0].equals("Bearer") && !splitted[1].isBlank();
  }

  public static String extractTokenFromAuthorizationHeaderValue(String authorizationHeaderValue) {
    var splitted = authorizationHeaderValue.split(" ");
    return splitted[1];
  }

  public Boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsernameFromToken(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  public String extractUsernameFromToken(String token) {
    return extractClaimFromToken(token, Claims::getSubject);
  }

  public Date extractExpirationFromToken(String token) {
    return extractClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T extractClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Boolean isTokenExpired(String token) {
    return extractExpirationFromToken(token).before(new Date());
  }

  private Claims extractAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

}
