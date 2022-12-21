package com.eazybytes.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RequestValidationBeforeAuthFilter implements Filter {

    private static final String AUTHENTICATION_SCHEME_BASIC = "Basic";
    private static final Charset CREDENTIALSCHARSET = StandardCharsets.UTF_8;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletReq = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResp = (HttpServletResponse) servletResponse;

        String header = httpServletReq.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null) {
            header = header.trim();
            if (StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BASIC)) {
                byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
                byte[] decoded;
                try {
                    decoded = Base64.getDecoder().decode(base64Token);
                    String token = new String(decoded, CREDENTIALSCHARSET);
                    int delim = token.indexOf(":");
                    if (delim == -1) {
                        throw new BadCredentialsException("Invalid basic authentication token");
                    }
                    String email = token.substring(0, delim);
                    if (email.toLowerCase().contains("test")) {
                        httpServletResp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        return;
                    }
                } catch (IllegalArgumentException e) {
                    throw new BadCredentialsException("Failed to decode basic authentication token");
                }
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }
}
