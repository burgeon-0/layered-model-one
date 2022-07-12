package org.bg181.sbd.adapter.security;

import org.springframework.security.core.AuthenticationException;

/**
 * 没有权限的异常信息
 *
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
