/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.seven.cow.servlet.logging.filters;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**
 * 自定义 HttpServletResponseWrapper
 *
 * @see HttpServletResponseWrapper
 */
class RequestCachingResponseWrapper extends org.springframework.web.util.ContentCachingResponseWrapper {

    private int status = 200;

    private final boolean alwaysOk;

    /**
     * Create a new ContentCachingResponseWrapper for the given servlet response.
     *
     * @param response the original servlet response
     */
    public RequestCachingResponseWrapper(HttpServletResponse response, boolean alwaysOk) {
        super(response);
        this.alwaysOk = alwaysOk;
    }

    @Override
    public int getStatus() {
        return (this.alwaysOk && this.status != 200) ? 404 : this.status;
    }

    public int getLocalStatus() {
        return this.status;
    }

    @Override
    public void setStatus(int sc) {
        this.status = sc;
        super.setStatus(sc);
    }

    @Override
    public void sendError(int sc)
            throws IOException {
        copyBodyToResponse(false);
        if (isCommitted()) {
            throw new IllegalStateException();
        }
        this.status = sc;
        if (!this.alwaysOk) {
            super.sendError(sc);
        }
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        copyBodyToResponse(false);
        if (isCommitted()) {
            throw new IllegalStateException(msg);
        }
        this.status = sc;
        if (!this.alwaysOk) {
            super.sendError(sc);
        }
    }

}
