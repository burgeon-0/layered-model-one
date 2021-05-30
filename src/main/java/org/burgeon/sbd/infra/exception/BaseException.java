package org.burgeon.sbd.infra.exception;

import lombok.Getter;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
public class BaseException extends RuntimeException {

    @Getter
    private int status;
    @Getter
    private int code;

    public BaseException(ErrorCode errorCode) {
        this(errorCode, errorCode.getMessage());
    }

    public BaseException(ErrorCode errorCode, String message) {
        super(message);
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
    }

}
