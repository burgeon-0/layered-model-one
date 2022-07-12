package org.bg181.sbd.core.exception;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
public class BizException extends BaseException {

    public BizException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BizException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

}
