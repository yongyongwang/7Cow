package com.seven.cow.servlet.logging.filters;

import org.springframework.util.StreamUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 自定义 HttpServletRequestWrapper
 *
 * @see HttpServletRequestWrapper
 */
class RequestCachingRequestWrapper extends ContentCachingRequestWrapper {

    private final ServletInputStream inputStream;
    private final ByteArrayOutputStream cachedContent;

    public RequestCachingRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        int contentLength = request.getContentLength();
        this.cachedContent = new ByteArrayOutputStream(contentLength >= 0 ? contentLength : 1024);
        this.inputStream = new ContentCachingInputStream(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() {
        return this.inputStream;
    }

    @Override
    public byte[] getContentAsByteArray() {
        return this.cachedContent.toByteArray();
    }

    private class ContentCachingInputStream extends ServletInputStream {

        private final ByteBuffer byteBuffer;

        public ContentCachingInputStream(ServletInputStream is) throws IOException {
            byte[] bytes = StreamUtils.copyToByteArray(is);
            cachedContent.write(bytes);
            this.byteBuffer = ByteBuffer.wrap(bytes).asReadOnlyBuffer();
        }

        @Override
        public int read() {
            if (isFinished()) {
                return -1;
            }
            return this.byteBuffer.get();
        }

        @Override
        public boolean isFinished() {
            return !this.byteBuffer.hasRemaining();
        }

        @Override
        public boolean isReady() {
            return this.byteBuffer.hasRemaining() || this.byteBuffer.remaining() > 0;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException();
        }
    }


}
