package com.lrrauseo.serverauth.security.filter;

import com.lrrauseo.serverauth.security.util.JwtUtil;
import com.lrrauseo.serverauth.service.CustomUserDetailsService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {
  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private CustomUserDetailsService service;

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  )
    throws ServletException, IOException {
    String token;
    String username;
    String authorizationHeader = request.getHeader("Authorization");
    if (
      authorizationHeader != null && authorizationHeader.startsWith("Bearer ")
    ) {
      token = authorizationHeader.substring(7);
      username = jwtUtil.extractUsername(token);

      if (
        username != null &&
        SecurityContextHolder.getContext().getAuthentication() == null
      ) {
        var userDetails = service.loadUserByUsername(username);
        if (jwtUtil.validateToken(token, userDetails)) {
          UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
          );
          usernamePasswordAuthenticationToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
          );
          SecurityContextHolder
            .getContext()
            .setAuthentication(usernamePasswordAuthenticationToken);
        }
      }
    }
    filterChain.doFilter(request, response);
  }
}
