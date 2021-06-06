package org.burgeon.sbd.adapter.security;

import org.burgeon.sbd.core.exception.ErrorCode;
import org.burgeon.sbd.core.res.Response;
import org.burgeon.sbd.infra.utils.JsonUtils;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Sam Lu
 * @date 2021/6/6
 */
public class SecurityCommon {

    public static final String X_INNER_SECURITY_EXCEPTION_RESPONDED = "X-Inner-Security-Exception-Responded";

    public static void response(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Response responseBody = new Response();
        responseBody.setCode(errorCode.getCode());
        responseBody.setMessage(errorCode.getMessage());
        response.getOutputStream().println(JsonUtils.toJson(responseBody));
    }

}
