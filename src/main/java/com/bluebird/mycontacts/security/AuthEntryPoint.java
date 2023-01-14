package com.bluebird.mycontacts.security;

import com.bluebird.mycontacts.extra.LoginResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        final LoginResult loginResult = new LoginResult(false, authException.getMessage());
        writer.println(loginResult);
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("BLUEBIRD");
        super.afterPropertiesSet();
    }
}
