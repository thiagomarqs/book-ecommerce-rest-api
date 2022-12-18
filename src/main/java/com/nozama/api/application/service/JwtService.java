package com.nozama.api.application.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

  @Value("${security.jwt.secret}")
  private String secret;

  @Value("${security.jwt.expiration}")
  private Long expirationInMillis;

  private SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userDetails.getUsername());
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
  
}