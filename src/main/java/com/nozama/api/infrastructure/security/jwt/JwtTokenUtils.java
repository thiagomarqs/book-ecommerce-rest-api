package com.nozama.api.infrastructure.security.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtils {

  @Value("${security.jwt:secret}")
  private String secret;

  @Value("${security.jwt.expiration}")
  private Long expirationInMillis;

  private SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userDetails.getUsername());
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private String createToken(Map<String, Object> claims, String subject) {
    var now = Instant.now();
    var issuedAt = Date.from(now);
    var nowPlusExpiration = now.plus(expirationInMillis, ChronoUnit.MILLIS);
    var expiration = Date.from(nowPlusExpiration);

    return Jwts.builder()
      .setClaims(claims)
      .setSubject(subject)
      .setIssuedAt(issuedAt)
      .setExpiration(expiration)
      .signWith(algorithm, secret)
      .compact();
  }
  
  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }  

}