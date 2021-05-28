package org.burgeon.sbd.controller.res;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author Sam Lu
 * @date 2021/5/28
 */
@Data
@NoArgsConstructor
public class SingleResponse<T> extends Response {

    private T data;

    public SingleResponse(int code, String message) {
        super(code, message);
    }

    public static SingleResponse ok(Object data) {
        SingleResponse response = new SingleResponse(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());
        response.setData(data);
        return response;
    }

}
