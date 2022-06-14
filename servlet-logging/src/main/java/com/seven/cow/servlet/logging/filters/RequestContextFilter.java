package com.seven.cow.servlet.logging.filters;

import com.seven.cow.servlet.logging.properties.LoggingProperties;
import com.seven.cow.servlet.logging.properties.Mode;
import com.seven.cow.servlet.logging.service.ResponseFilterService;
import com.seven.cow.spring.boot.autoconfigure.constant.Cants;
import com.seven.cow.spring.boot.autoconfigure.util.CurrentContext;
import com.seven.cow.spring.boot.autoconfigure.util.DataSizeUtil;
import com.seven.cow.spring.boot.autoconfigure.util.LoggerUtils;
import com.seven.cow.spring.boot.autoconfigure.util.VUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;

import static com.seven.cow.spring.boot.autoconfigure.constant.Cants.*;

public class RequestContextFilter extends OncePerRequestFilter implements Ordered {

    @Resource
    private LoggingProperties loggingProperties;

    private static final AntPathMatcher matcher = new AntPathMatcher();

    @Resource
    private ResponseFilterService responseFilterService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String requestUrl = httpServletRequest.getRequestURL().toString();
        String queryString = httpServletRequest.getQueryString();
        boolean isLog = CurrentContext.take(Cants.X_CURRENT_REQUEST_LOGGING, false);
        if (!StringUtils.isEmpty(queryString)) {
            queryString = URLDecoder.decode(queryString, httpServletRequest.getCharacterEncoding());
            requestUrl += "?" + queryString;
        }
        String method = httpServletRequest.getMethod();
        info(isLog, ">>>>>> > Begin RequestURL: " + requestUrl);
        info(isLog, "------ > Request Method: " + method);

        // region 读取请求参数
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headName = headerNames.nextElement();
            CurrentContext.set(X_CURRENT_HEADERS + SPLIT_COLON + headName, httpServletRequest.getHeader(headName));
        }
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
        List<String> parameters = new ArrayList<>();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            String parameterValue = String.join(SPLIT_COMMA, Arrays.asList(httpServletRequest.getParameterValues(parameterName)));
            parameters.add((parameterName + "=" + parameterValue));
            CurrentContext.set(X_CURRENT_REQUEST_PARAMETERS + SPLIT_COLON + parameterName, parameterValue);
        }
        RequestCachingRequestWrapper cachingRequestWrapper = new RequestCachingRequestWrapper(httpServletRequest);
        RequestCachingResponseWrapper cachingResponseWrapper = new RequestCachingResponseWrapper(httpServletResponse, loggingProperties.isAlwaysNotFound());
        // endregion 读取请求参数

        byte[] reqBytes = cachingRequestWrapper.getContentAsByteArray();
        CurrentContext.set(X_CURRENT_REQUEST_BODY, reqBytes);
        VUtils.choose(() -> !CollectionUtils.isEmpty(parameters) ? 0 : 1).handle(() -> info(isLog, "------ > Request Parameters: " + String.join("&", parameters)));
        String payload = new String(reqBytes, cachingRequestWrapper.getCharacterEncoding());
        VUtils.choose(() -> !StringUtils.isEmpty(payload) ? 0 : 1).handle(() -> info(isLog, "------ > Request Payload(" + DataSizeUtil.format(reqBytes.length) + "): " + payload));
        try {
            filterChain.doFilter(cachingRequestWrapper, cachingResponseWrapper);
        } catch (Throwable ex) {
            CurrentContext.set(X_CURRENT_REQUEST_EXCEPTION, ex);
            if (!loggingProperties.isAlwaysNotFound()) {
                cachingResponseWrapper.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            } else {
                cachingResponseWrapper.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        } finally {
            byte[] rtnValue = cachingResponseWrapper.getContentAsByteArray();
            VUtils.choose(() -> CurrentContext.existsKey(X_CURRENT_REQUEST_EXCEPTION) ? 0 : 1).handle(() -> error(CurrentContext.take(X_CURRENT_REQUEST_EXCEPTION)));
            HttpStatus rspStatus = HttpStatus.valueOf(cachingResponseWrapper.getStatus());
            rtnValue = responseFilterService.handle(rspStatus.value(), rtnValue);
            if (!httpServletResponse.isCommitted()) {
                httpServletResponse.setStatus(rspStatus.value());
                httpServletResponse.getOutputStream().write(rtnValue);
            }
            info(isLog, "< ------ Response(" + (loggingProperties.isAlwaysNotFound() ? cachingResponseWrapper.getLocalStatus() : rspStatus.value()) + "|" + rspStatus.getReasonPhrase() + ") Data(" + DataSizeUtil.format(rtnValue.length) + "): " + new String(rtnValue, cachingRequestWrapper.getCharacterEncoding()));
            info(isLog, "< <<<<<< End RequestURL: " + requestUrl);
            CurrentContext.remove();
        }

    }

    private void info(boolean isLog, String message) {
        if (loggingProperties.getPrint() && isLog) {
            LoggerUtils.info(message);
        }
    }

    private void error(Throwable ex) {
        if (loggingProperties.getPrint()) {
            LoggerUtils.error("rest process exception:", ex);
        }
    }

    protected boolean shouldNotFilter(HttpServletRequest httpServletRequest) throws ServletException {
        String requestPath = httpServletRequest.getRequestURI();
        boolean isMatchRequest = matchRequest(requestPath);
        CurrentContext.set(Cants.X_CURRENT_REQUEST_LOGGING, !isMatchRequest);
        if (Mode.filter.equals(loggingProperties.getMode())) {
            return isMatchRequest;
        } else {
            return super.shouldNotFilter(httpServletRequest);
        }
    }

    private boolean matchRequest(String requestPath) {
        List<String> includePatterns = loggingProperties.getIncludePatterns();
        List<String> excludePatterns = this.loggingProperties.getExcludePatterns();
        if (CollectionUtils.isEmpty(includePatterns)) {
            if (CollectionUtils.isEmpty(excludePatterns)) {
                excludePatterns = Collections.emptyList();
            }
            return excludePatterns.stream().anyMatch(pattern -> matcher.match(pattern, requestPath));
        } else {
            if (!CollectionUtils.isEmpty(excludePatterns)) {
                return includePatterns.stream().noneMatch(pattern -> matcher.match(pattern, requestPath)) && excludePatterns.stream().anyMatch(pattern -> matcher.match(pattern, requestPath));
            }
            return includePatterns.stream().noneMatch(pattern -> matcher.match(pattern, requestPath));
        }
    }

    @Override
    public int getOrder() {
        return loggingProperties.getOrder();
    }
}
