package com.bluebird.mycontacts.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        final Map<String, Object> result = new HashMap<>();
        result.put("status", false);
        result.put("message", authException.getMessage());
        result.put("token-type", null);
        result.put("token", null);
        writer.println(result);
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("BLUEBIRD");
        super.afterPropertiesSet();
    }
}
