package org.bg181.sbd.core.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @author Sam Lu
 * @date 2021/5/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response implements Serializable {

    private int code;
    private String message;

    public static Response ok() {
        return new Response(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase());
    }

    public static Response created() {
        return new Response(HttpStatus.CREATED.value(), HttpStatus.CREATED.getReasonPhrase());
    }

}
