package com.lrrauseo.serverauth.security.util;

import com.lrrauseo.serverauth.model.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
public class JwtUtil {
  Key key;

  public JwtUtil() {
    key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
  }

  public String generateToken(Authentication userAuthenticated) {
    Map<String, Object> claims = new HashMap<>();
    var user = (CustomUserDetails) userAuthenticated.getPrincipal();
    claims.put("Roles", userAuthenticated.getAuthorities());

    return createToken(claims, user.getUsername());
  }

  private String createToken(Map<String, Object> claims, String subject) {
    return Jwts
      .builder()
      .setClaims(claims)
      .setSubject(subject)
      .setIssuedAt(new Date(System.currentTimeMillis()))
      .setExpiration(new Date(System.currentTimeMillis() + 900000))
      .signWith(key)
      .compact();
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (
      username.equals(userDetails.getUsername()) && !isTokenExpired(token)
    );
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  private Claims extractAllClaims(String token) {
    return (Claims) Jwts
      .parserBuilder()
      .setSigningKey(key)
      .build()
      .parse(token)
      .getBody();
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }
}
