package org.burgeon.sbd.adapter.security;

import lombok.extern.slf4j.Slf4j;
import org.burgeon.sbd.core.exception.ErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 在AuthenticationEntryPoint中会丢失业务抛出的AuthenticationException信息
 *
 * @author Sam Lu
 * @date 2021/6/5
 * @see RestAuthenticationFailureHandler
 */
@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {
        Boolean exceptionResponded = (Boolean) request.getAttribute(SecurityCommon.X_INNER_SECURITY_EXCEPTION_RESPONDED);
        if (exceptionResponded == null || !exceptionResponded) {
            SecurityCommon.response(response, ErrorCode.UNAUTHORIZED);
        }
    }

}
