package org.burgeon.sbd.adapter.security;

import lombok.extern.slf4j.Slf4j;
import org.burgeon.sbd.core.exception.ErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Sam Lu
 * @date 2021/6/6
 */
@Slf4j
public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String uri = String.format("%s %s", request.getMethod(), request.getRequestURI());
        log.warn("[Exception] [{}], Authentication Failure: {}", uri, exception.getMessage());

        if (exception instanceof SecurityUnauthorizedException) {
            SecurityCommon.response(response, ErrorCode.UNAUTHORIZED);
        } else if (exception instanceof SecurityForbiddenException) {
            SecurityCommon.response(response, ErrorCode.FORBIDDEN);
        } else {
            SecurityCommon.response(response, ErrorCode.UNAUTHORIZED);
        }
        request.setAttribute(SecurityCommon.X_INNER_SECURITY_EXCEPTION_RESPONDED, true);
    }

}
