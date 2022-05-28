package com.seven.cow.servlet.download.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seven.cow.servlet.download.service.DownloadFileService;
import com.seven.cow.spring.boot.autoconfigure.entity.ResponseCmd;
import com.seven.cow.spring.boot.autoconfigure.entity.file.FileInfo;
import com.seven.cow.spring.boot.autoconfigure.util.LoggerUtils;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2021/11/26 10:26
 * @version: 1.0
 */
public class DefaultDownloadFileServiceImpl implements DownloadFileService {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] download(String fileKey, String storeAddress) {
        String filePath = (storeAddress + "/" + fileKey);
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            try (FileInputStream inputStream = new FileInputStream(file)) {
                byte[] bytes = StreamUtils.copyToByteArray(inputStream);
                FileInfo fileInfo = new FileInfo(bytes.length) {
                    @Override
                    public String key() {
                        return String.format("%02X", System.nanoTime());
                    }
                };
                String fInfo = null;
                try {
                    fInfo = objectMapper.writeValueAsString(fileInfo);
                } catch (JsonProcessingException e) {
                    LoggerUtils.error("cast to json exception:", e);
                }
                LoggerUtils.info("Download a file successful:" + fInfo);
                return bytes;
            } catch (Exception ex) {
                LoggerUtils.error("Download a file failure:", ex);
            }
        } else {
            try {
                return objectMapper.writeValueAsBytes(ResponseCmd.fail().message("fileKey:" + fileKey + " is missing!"));
            } catch (JsonProcessingException e) {
                LoggerUtils.error(e.getMessage(), e);
            }
        }
        return new byte[0];
    }
}
