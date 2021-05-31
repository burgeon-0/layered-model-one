package org.burgeon.sbd.infra.exception;

import org.springframework.http.HttpStatus;

/**
 * 错误码格式：xxxyyyzzzz，xxx为系统编码，yyy为HTTP状态码，zzzz为错误编码；
 * 本系统编码为：100；
 * 默认域错误编码：10zz；
 * 用户域错误编码：11zz；
 * 产品域错误编码：12zz；
 * 订单域错误编码：13zz；
 *
 * @author Sam Lu
 * @date 2021/5/30
 */
public enum ErrorCode {

    /**
     * 产品库存不足
     */
    PRODUCT_STOCK_NOT_ENOUGH(HttpStatus.BAD_REQUEST.value(), 1004001201, "Product Stock Not Enough"),
    /**
     * 订单项为空
     */
    ORDER_ITEM_NULL(HttpStatus.BAD_REQUEST.value(), 1004001301, "Order Item Must Not Be Null"),

    /**
     * 找不到资源，包含URI资源和数据资源等
     */
    SOURCE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1004041000, HttpStatus.NOT_FOUND.getReasonPhrase()),
    /**
     * 找不到用户
     */
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1004041100, "User Not Found"),
    /**
     * 找不到产品
     */
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1004041200, "Product Not Found"),
    /**
     * 找不到订单
     */
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), 1004041300, "Order Not Found");

    private int status;
    private int code;
    private String message;

    ErrorCode(int status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
