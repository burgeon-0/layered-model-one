package org.bg181.sbd.adapter.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sam Lu
 * @date 2021/6/5
 */
@Slf4j
public class PreAuthenticatedFilter extends AbstractPreAuthenticatedProcessingFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    public PreAuthenticatedFilter(AuthenticationManager authenticationManager) {
        setAuthenticationFailureHandler(new RestAuthenticationFailureHandler());
        setAuthenticationManager(authenticationManager);
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String uri = String.format("%s %s", request.getMethod(), request.getRequestURI());
        String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader == null) {
            log.warn("[Exception] [{}], Authorization Header Not Found", uri);
            return null;
        }

        if (!authHeader.startsWith(TOKEN_PREFIX)) {
            log.warn("[Exception] [{}], Authorization Header Invalid: {}", uri, authHeader);
            return null;
        }

        String token = authHeader.substring(TOKEN_PREFIX.length());
        return token;
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return null;
    }

}
