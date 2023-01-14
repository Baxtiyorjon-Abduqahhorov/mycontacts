package com.bluebird.mycontacts.security;

import com.bluebird.mycontacts.entities.Users;
import com.bluebird.mycontacts.repositories.UsersRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class TokenFilter extends OncePerRequestFilter {

    private final TokenGenerator tokenGenerator;
    private final CustomUserDetailsService customUserDetailsService;

    public TokenFilter(TokenGenerator tokenGenerator, CustomUserDetailsService customUserDetailsService) {
        this.tokenGenerator = tokenGenerator;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = tokenGenerator.getTokenFromRequest(request);
        if (token != null && !token.isEmpty() && tokenGenerator.validateToken(token)) {
            final String phone = tokenGenerator.getUsernameFromToken(token);
            final UserDetails userDetails = customUserDetailsService.loadUserByUsername(phone);
            final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return new AntPathMatcher().match("/api/auth/**", request.getServletPath());
    }
}
