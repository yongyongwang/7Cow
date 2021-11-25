package com.seven.cow.servlet.logging.filters;

import com.seven.cow.servlet.logging.properties.LoggingProperties;
import com.seven.cow.spring.boot.autoconfigure.util.CurrentContext;
import com.seven.cow.spring.boot.autoconfigure.util.LoggerUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import static com.seven.cow.spring.boot.autoconfigure.constant.Conts.*;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2021/11/23 11:18
 * @version: 1.0
 */
public class RequestContextFilter extends OncePerRequestFilter implements Ordered {

    @Resource
    private LoggingProperties loggingProperties;

    private static final AntPathMatcher matcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper cachingRequestWrapper = new ContentCachingRequestWrapper(httpServletRequest);
        ContentCachingResponseWrapper cachingResponseWrapper = new ContentCachingResponseWrapper(httpServletResponse, loggingProperties.isAlwaysOk());
        String requestUrl = httpServletRequest.getRequestURL().toString();
        String queryString = httpServletRequest.getQueryString();
        if (!StringUtils.isEmpty(queryString)) {
            queryString = URLDecoder.decode(queryString, "UTF-8");
            requestUrl += "?" + queryString;
        }
        String method = httpServletRequest.getMethod();
        LoggerUtils.info(">>>>>> > Begin RequestURL: " + requestUrl);
        LoggerUtils.info("------ > Request Method: " + method);

        // region 读取请求参数
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headName = headerNames.nextElement();
            CurrentContext.set(X_CURRENT_HEADERS + SPLIT_COLON + headName, httpServletRequest.getHeader(headName));
        }
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            CurrentContext.set(X_CURRENT_REQUEST_PARAMETERS + SPLIT_COLON + parameterName, String.join(",", Arrays.asList(httpServletRequest.getParameterValues(parameterName))));
        }
        // endregion 读取请求参数

        try {
            filterChain.doFilter(cachingRequestWrapper, cachingResponseWrapper);
        } catch (Throwable ex) {
            CurrentContext.set(X_CURRENT_REQUEST_EXCEPTION, ex);
            cachingResponseWrapper.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
        } finally {
            byte[] reqBytes = cachingRequestWrapper.getContentAsByteArray();
            if (!CurrentContext.existsKey(X_CURRENT_REQUEST_REST_INPUT)) {
                reqBytes = FileCopyUtils.copyToByteArray(cachingRequestWrapper.getInputStream());
            }
            CurrentContext.set(X_CURRENT_REQUEST_BODY, reqBytes);
            LoggerUtils.info("------ > Request Payload: " + new String(reqBytes, cachingRequestWrapper.getCharacterEncoding()));
            byte[] rtnValue = cachingResponseWrapper.getContentAsByteArray();
            if (CurrentContext.existsKey(X_CURRENT_REQUEST_REST_INPUT)) {
                LoggerUtils.info(CurrentContext.take(X_CURRENT_REQUEST_REST_INPUT));
            }
            if (CurrentContext.existsKey(X_CURRENT_REQUEST_EXCEPTION)) {
                LoggerUtils.info("rest process exception:", (Throwable) CurrentContext.take(X_CURRENT_REQUEST_EXCEPTION));
            }
            if (CurrentContext.existsKey(X_CURRENT_REQUEST_REST_OUTPUT)) {
                LoggerUtils.info(CurrentContext.take(X_CURRENT_REQUEST_REST_OUTPUT));
            }
            HttpStatus rspStatus = HttpStatus.valueOf(cachingResponseWrapper.getErrorStatus());
            if (!httpServletResponse.isCommitted()) {
                httpServletResponse.setStatus(rspStatus.value());
                httpServletResponse.getOutputStream().write(rtnValue);
            }
            LoggerUtils.info("< ------ Response(" + rspStatus.value() + "|" + rspStatus.getReasonPhrase() + ") Data: " + new String(rtnValue, cachingResponseWrapper.getCharacterEncoding()));
            LoggerUtils.info("< <<<<<< End RequestURL: " + requestUrl);
            CurrentContext.remove();
        }

    }

    protected boolean shouldNotFilter(HttpServletRequest httpServletRequest) throws ServletException {
        String requestPath = httpServletRequest.getRequestURI();
        String contentPath = httpServletRequest.getServletContext().getContextPath();
        List<String> patterns = this.loggingProperties.getExcludePatterns();
        if (CollectionUtils.isEmpty(patterns)) {
            patterns = new ArrayList<>(0);
        }
        return patterns.stream().anyMatch(pattern -> matcher.match(pattern, requestPath));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
