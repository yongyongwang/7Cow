package com.seven.cow.servlet.upload.filters;

import com.seven.cow.servlet.upload.properties.UploadProperties;
import com.seven.cow.servlet.upload.service.UploadFileService;
import com.seven.cow.spring.boot.autoconfigure.entity.file.FileInfo;
import org.springframework.core.Ordered;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2021/11/25 19:32
 * @version: 1.0
 */
public class FileUploadFilter extends OncePerRequestFilter implements Ordered {

    @Resource
    private UploadProperties uploadProperties;

    @Resource
    private UploadFileService uploadFileService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        byte[] bytes = StreamUtils.copyToByteArray(request.getInputStream());
        FileInfo fileInfo = uploadFileService.upload(bytes, uploadProperties.getStoreAddress());
        byte[] json = uploadFileService.writeResponseInfo(fileInfo);
        response.getOutputStream().write(json);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String contentPath = request.getServletContext().getContextPath();
        String requestPath = request.getRequestURI();
        return !("POST".equalsIgnoreCase(request.getMethod())
                && "application/octet-stream".equalsIgnoreCase(request.getContentType())
                && requestPath.equalsIgnoreCase((contentPath + uploadProperties.getAddress())));
    }

    @Override
    public int getOrder() {
        return uploadProperties.getOrder();
    }
}
