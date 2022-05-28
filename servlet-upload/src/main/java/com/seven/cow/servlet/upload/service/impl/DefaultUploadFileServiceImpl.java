package com.seven.cow.servlet.upload.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seven.cow.servlet.upload.service.UploadFileService;
import com.seven.cow.spring.boot.autoconfigure.entity.ResponseCmd;
import com.seven.cow.spring.boot.autoconfigure.entity.file.FileInfo;
import com.seven.cow.spring.boot.autoconfigure.util.LoggerUtils;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2021/11/25 20:46
 * @version: 1.0
 */
public class DefaultUploadFileServiceImpl implements UploadFileService {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public FileInfo upload(byte[] content, String storeAddress) {
        FileInfo fileInfo = new FileInfo(content.length) {
            @Override
            public String key() {
                return String.format("%02X", System.nanoTime());
            }
        };
        // Save the dir
        File dir = new File(storeAddress);
        if (!dir.exists() && dir.mkdirs()) {
            LoggerUtils.info("Create a directory:" + storeAddress);
        }
        // Check saveDirectory is truly a directory
        if (!dir.isDirectory())
            throw new IllegalArgumentException("Not a directory: " + storeAddress);

        // Check saveDirectory is writable
        if (!dir.canWrite())
            throw new IllegalArgumentException("Not writable: " + storeAddress);
        String fInfo = null;
        try {
            fInfo = objectMapper.writeValueAsString(fileInfo);
        } catch (JsonProcessingException e) {
            LoggerUtils.error("cast to json exception:", e);
        }
        try (FileOutputStream outputStream = new FileOutputStream(storeAddress + "/" + fileInfo.key())) {
            FileCopyUtils.copy(content, outputStream);
            LoggerUtils.info("Upload a file successful:" + fInfo);
        } catch (IOException e) {
            LoggerUtils.error("Upload a file failure:" + fInfo, e);
        }
        return fileInfo;
    }

    @Override
    public byte[] writeResponseInfo(FileInfo fileInfo) {
        try {
            return objectMapper.writeValueAsBytes(ResponseCmd.ok(fileInfo));
        } catch (JsonProcessingException e) {
            LoggerUtils.error("cast to json exception:", e);
        }
        return new byte[0];
    }

}
