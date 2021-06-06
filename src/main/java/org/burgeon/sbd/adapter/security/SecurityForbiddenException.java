package org.burgeon.sbd.adapter.security;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Sam Lu
 * @date 2021/6/5
 */
public class SecurityForbiddenException extends AuthenticationException {

    public SecurityForbiddenException(String msg) {
        super(msg);
    }

    public SecurityForbiddenException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
