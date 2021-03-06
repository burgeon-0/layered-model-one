package org.bg181.sbd.adapter.common;

import lombok.extern.slf4j.Slf4j;
import org.bg181.sbd.infra.Constants;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * @author Sam Lu
 * @date 2021/6/4
 */
@Slf4j
public class FilterRequestWrapper extends HttpServletRequestWrapper {

    private boolean cached;
    private StringBuilder cachedContent;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public FilterRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (cached) {
            return new WrapperInputStream(super.getInputStream());
        }
        return super.getInputStream();
    }

    public String getContentAsString() {
        if (!cached) {
            read();
        }
        return cachedContent.toString();
    }

    public byte[] getContentAsByteArray() {
        if (!cached) {
            read();
        }
        try {
            String content = cachedContent.toString();
            return content.getBytes(Constants.ENCODING_UTF8);
        } catch (UnsupportedEncodingException e) {
            log.error("Unsupported Encoding Exception", e);
        }
        return null;
    }

    private void read() {
        try {
            InputStream is = super.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, Constants.ENCODING_UTF8));
            cachedContent = new StringBuilder();
            for (int n; (n = br.read()) != -1; ) {
                cachedContent.append((char) n);
            }
            cached = true;
        } catch (IOException e) {
            log.error("Read Request Steam Fail", e);
        }
    }

    private class WrapperInputStream extends ServletInputStream {

        private ServletInputStream inputStream;
        private ByteArrayInputStream stream;

        public WrapperInputStream(ServletInputStream inputStream) {
            try {
                this.inputStream = inputStream;
                String content = cachedContent.toString();
                byte[] bytes = content.getBytes(Constants.ENCODING_UTF8);
                stream = new ByteArrayInputStream(bytes);
            } catch (UnsupportedEncodingException e) {
                log.error("Unsupported Encoding Exception", e);
            }
        }

        @Override
        public int read() throws IOException {
            return stream.read();
        }

        @Override
        public boolean isFinished() {
            return inputStream.isFinished();
        }

        @Override
        public boolean isReady() {
            return inputStream.isReady();
        }

        @Override
        public void setReadListener(ReadListener listener) {
            inputStream.setReadListener(listener);
        }

    }

}
