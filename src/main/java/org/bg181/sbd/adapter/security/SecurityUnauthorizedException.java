package org.bg181.sbd.adapter.security;

import org.springframework.security.core.AuthenticationException;

/**
 * 认证失败的异常信息
 *
 * @author Sam Lu
 * @date 2021/6/5
 */
public class SecurityUnauthorizedException extends AuthenticationException {

    public SecurityUnauthorizedException(String msg) {
        super(msg);
    }

    public SecurityUnauthorizedException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
