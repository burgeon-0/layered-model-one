package org.burgeon.sbd.infra.exception;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
public class ParamException extends BaseException {

    public ParamException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ParamException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

}
