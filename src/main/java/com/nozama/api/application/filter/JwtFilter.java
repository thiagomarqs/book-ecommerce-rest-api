package com.nozama.api.application.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nozama.api.application.util.JwtUtil;

@Component
public class JwtFilter extends OncePerRequestFilter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    final var authorizationHeaderValue = request.getHeader("Authorization");
    
    if(JwtUtil.isAuthorizationHeaderValid(authorizationHeaderValue)) {

      final var token = JwtUtil.extractTokenFromAuthorizationHeaderValue(authorizationHeaderValue);
      final var username = jwtUtil.extractUsernameFromToken(token);
      final var isAuthenticatedAlready = SecurityContextHolder.getContext().getAuthentication() != null;
      final var shouldTryAuthentication = username != null && !isAuthenticatedAlready;

      if(shouldTryAuthentication) tryAuthentication(request, token, username);

    }
    
    filterChain.doFilter(request, response);
  }

  private void tryAuthentication(HttpServletRequest request, final String token, final String username) {
    final var userDetails = userDetailsService.loadUserByUsername(username);   
    final var authorities = userDetails.getAuthorities();
    final var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
  
}
