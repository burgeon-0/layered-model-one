package org.burgeon.sbd.adapter.common;

import lombok.extern.slf4j.Slf4j;
import org.burgeon.sbd.core.SnKeeper;
import org.burgeon.sbd.infra.utils.JsonUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sam Lu
 * @date 2021/6/3
 */
@Slf4j
public class LoggableDispatcherServlet extends DispatcherServlet {

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            if (!(request instanceof ContentCachingRequestWrapper)) {
                request = new ContentCachingRequestWrapper(request);
            }
            if (!(response instanceof ContentCachingResponseWrapper)) {
                response = new ContentCachingResponseWrapper(response);
            }
        } finally {
            Thread thread = Thread.currentThread();
            String oldThreadName = thread.getName();
            try {
                String newThreadName = SnKeeper.get(LoggableDispatcherServlet.class.getSimpleName());
                thread.setName(newThreadName);
                super.doDispatch(request, response);
            } finally {
                logRequest(request);
                logResponse(request, response);
                updateResponse(response);
                thread.setName(oldThreadName);
            }
        }
    }

    private void logRequest(HttpServletRequest request) {
        try {
            String uri = getUri(request);
            String queryString = request.getQueryString();
            String body = getBody(request);
            Map<String, String> headers = getHeaders(request);

            log.info("[  Request] [{}], QueryString: {}, Body: {}, Headers: {}",
                    uri, queryString, body, JsonUtils.toJson(headers));
        } catch (Exception e) {
            log.warn("Log Request Fail: {}", e.getMessage());
        }
    }

    private void logResponse(HttpServletRequest request, HttpServletResponse response) {
        try {
            String uri = getUri(request);
            String body = getBody(response);
            Map<String, String> headers = getHeaders(response);

            log.info("[ Response] [{}], Status: {}, Body: {}, Headers: {}",
                    uri, response.getStatus(), body, JsonUtils.toJson(headers));
        } catch (Exception e) {
            log.warn("Log Response Fail: {}", e.getMessage());
        }
    }

    private String getUri(HttpServletRequest request) {
        String uri = String.format("%s %s", request.getMethod(), request.getRequestURI());
        return uri;
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        return headers;
    }

    private Map<String, String> getHeaders(HttpServletResponse response) {
        Map<String, String> headers = new HashMap<>();
        Collection<String> headerNames = response.getHeaderNames();
        for (String headerName : headerNames) {
            headers.put(headerName, response.getHeader(headerName));
        }
        return headers;
    }

    private String getBody(HttpServletRequest request) {
        ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) request;
        String body = null;
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            String encoding = wrapper.getCharacterEncoding();
            body = getBody(buf, encoding);
            if (body != null) {
                body = body.replaceAll("\n", "");
                body = body.replaceAll("\t", "");
            }
        }
        return body;
    }

    private String getBody(HttpServletResponse response) {
        ContentCachingResponseWrapper wrapper = (ContentCachingResponseWrapper) response;
        String body = null;
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            String encoding = wrapper.getCharacterEncoding();
            body = getBody(buf, encoding);
        }
        return body;
    }

    private String getBody(byte[] buf, String encoding) {
        if (buf.length > 0) {
            int length = Math.min(buf.length, 5120);
            try {
                return new String(buf, 0, length, encoding);
            } catch (UnsupportedEncodingException e) {
                log.warn("Unsupported Encoding Exception: {}", e.getMessage());
            }
        }
        return null;
    }

    private void updateResponse(HttpServletResponse response) throws IOException {
        ContentCachingResponseWrapper wrapper = (ContentCachingResponseWrapper) response;
        wrapper.copyBodyToResponse();
    }

}
