package org.burgeon.sbd.adapter.common;

import lombok.extern.slf4j.Slf4j;
import org.burgeon.sbd.core.exception.BaseException;
import org.burgeon.sbd.core.exception.ErrorCode;
import org.burgeon.sbd.core.res.Response;
import org.burgeon.sbd.infra.utils.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletException;
import java.util.List;

/**
 * TODO 改进日志打印
 *
 * @author Sam Lu
 * @date 2021/6/2
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String JSON_PARSE_ERROR_CCI = "Cannot construct instance";
    private static final String JSON_PARSE_ERROR_CDV = "Cannot deserialize value";
    private static final String JSON_PARSE_ERROR_UC = "Unexpected character";

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn("Http Message Not Readable Exception -> {}: {}", e.getClass().getName(), e.getMessage());
        // eg: JSON parse error: Unexpected character (':' (code 46)): was expecting comma to separate Object entries;
        String message = e.getMessage();
        String spilt1 = ": ";
        String spilt2 = "; ";
        if (message.contains(spilt1)) {
            String msg1 = message.substring(0, message.indexOf(spilt1));
            String msg2 = message.substring(message.indexOf(spilt1) + 2);
            if (msg2.startsWith(JSON_PARSE_ERROR_CCI)) {
                message = msg1 + spilt1 + JSON_PARSE_ERROR_CCI;
            } else if (msg2.startsWith(JSON_PARSE_ERROR_CDV)) {
                message = msg1 + spilt1 + JSON_PARSE_ERROR_CDV;
            } else {
                int start = 0;
                if (msg2.startsWith(JSON_PARSE_ERROR_UC)) {
                    // eg: Unexpected character (':' (code 46)): was expecting comma to separate Object entries;
                    start = msg2.indexOf("' (code ");
                }
                int index1 = msg2.indexOf(spilt1, start);
                int index2 = msg2.indexOf(spilt2, start);
                if (index1 > -1 && index2 > -1) {
                    msg2 = msg2.substring(0, Math.min(index1, index2));
                } else {
                    msg2 = msg2.substring(0, Math.max(index1, index2));
                }
                message = msg2.length() > 0 ? msg1 + spilt1 + msg2 : msg1;
            }
        }
        return new Response(ErrorCode.PARAM_INVALID.getCode(), message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ObjectError> errors = e.getAllErrors();
        String message = getErrorMessage(errors);
        return new Response(ErrorCode.PARAM_INVALID.getCode(), message);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleBindException(BindException e) {
        log.warn("Bind Exception -> {}: {}", e.getClass().getName(), e.getMessage());
        String message = getErrorMessage(e.getAllErrors());
        return new Response(ErrorCode.PARAM_INVALID.getCode(), message);
    }

    private String getErrorMessage(List<ObjectError> errors) {
        StringBuilder buf = new StringBuilder();
        for (ObjectError error : errors) {
            if (error instanceof FieldError) {
                String fieldName = ((FieldError) error).getField();
                fieldName = StringUtils.camelCaseToSnakeCase(fieldName);
                buf.append("[").append(fieldName).append("] ");
                buf.append(error.getDefaultMessage()).append("; ");
            } else {
                log.warn("Method Argument Not Valid Error: {}", error.toString());
            }
        }
        String message = buf.substring(0, buf.length() - 2);
        return message;
    }

    @ExceptionHandler(ServletException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Response handleServletException(ServletException e) {
        log.warn("Servlet Exception -> {}: {}", e.getClass().getName(), e.getMessage());
        return new Response(ErrorCode.PARAM_INVALID.getCode(), e.getMessage());
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Response> handleBaseException(BaseException e) {
        Response response = new Response(e.getCode(), e.getMessage());
        return new ResponseEntity(response, HttpStatus.valueOf(e.getStatus()));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Response handleRuntimeException(RuntimeException e) {
        log.error("Internal Server Error", e);
        return new Response(ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
    }

}
