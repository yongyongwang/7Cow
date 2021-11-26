package com.seven.cow.servlet.download.filters;

import com.seven.cow.servlet.download.properties.DownloadProperties;
import com.seven.cow.spring.boot.autoconfigure.util.LoggerUtils;
import org.springframework.core.Ordered;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2021/11/26 9:04
 * @version: 1.0
 */
public class FileDownloadFilter extends OncePerRequestFilter implements Ordered {

    @Resource
    private DownloadProperties downloadProperties;

    private static final AntPathMatcher matcher = new AntPathMatcher();

    @Override
    public int getOrder() {
        return downloadProperties.getOrder();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String contentPath = request.getServletContext().getContextPath();
        String requestPath = request.getRequestURI();
        Map<String, String> variables = matcher.extractUriTemplateVariables((contentPath + downloadProperties.getAddress()), requestPath);
        String fileKey = variables.get(downloadProperties.getKeyName());
        String saveDirectory = downloadProperties.getStoreAddress();
        String filePath = (saveDirectory + "/" + fileKey);
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            try (FileInputStream inputStream = new FileInputStream(file)) {
                byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
                LoggerUtils.info("Download A File Successful,FileKey:" + fileKey + ",Size:" + bytes.length + " !");
                response.getOutputStream().write(bytes);
            } catch (Exception ex) {
                LoggerUtils.error("Download A File Failure,FileKey:" + fileKey + "!", ex);
            }
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String contentPath = request.getServletContext().getContextPath();
        String requestPath = request.getRequestURI();
        return "GET".equalsIgnoreCase(request.getMethod())
                && matcher.match((contentPath + downloadProperties.getAddress()), requestPath);
    }


}
