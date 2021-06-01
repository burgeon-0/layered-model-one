package org.burgeon.sbd.core.exception;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
public class SysException extends BaseException {

    public SysException(ErrorCode errorCode) {
        super(errorCode);
    }

    public SysException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

}
