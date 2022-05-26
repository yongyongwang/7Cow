package com.seven.cow.servlet.upload.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seven.cow.servlet.upload.service.UploadFileService;
import com.seven.cow.spring.boot.autoconfigure.util.DataSizeUtil;
import com.seven.cow.spring.boot.autoconfigure.util.LoggerUtils;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2021/11/25 20:46
 * @version: 1.0
 */
public class DefaultUploadFileServiceImpl implements UploadFileService {
    @Override
    public FileInfo upload(byte[] content, String storeAddress) {
        DefaultFileInfo fileInfo = new DefaultFileInfo(content.length);
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
        String fileKey = fileInfo.key();
        try (FileOutputStream outputStream = new FileOutputStream(storeAddress + "/" + fileInfo.key())) {
            FileCopyUtils.copy(content, outputStream);
            LoggerUtils.info("Upload a file successful,FileKey:" + fileKey + ",Size:" + fileInfo.humanReadableSize() + " !");
        } catch (IOException e) {
            LoggerUtils.error("Upload a file failure,FileKey:" + fileKey + ",Size:" + fileInfo.humanReadableSize() + " !", e);
        }
        return fileInfo;
    }

    @Override
    public byte[] writeResponseInfo(FileInfo fileInfo) {
        try {
            return new ObjectMapper().writeValueAsBytes(new HashMap(1) {{
                put("fileKey", fileInfo.key());
            }});
        } catch (JsonProcessingException e) {
            LoggerUtils.error("cast to json exception:", e);
        }
        return new byte[0];
    }

    static class DefaultFileInfo extends FileInfo {

        private final int size;

        private final String key;

        public DefaultFileInfo(int size) {
            this.size = size;
            this.key = String.format("%02X", System.nanoTime());
        }

        @Override
        public String key() {
            return this.key;
        }

        public String humanReadableSize() {
            return DataSizeUtil.format(size);
        }
    }
}
