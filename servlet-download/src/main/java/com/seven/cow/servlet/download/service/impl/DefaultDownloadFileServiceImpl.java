package com.seven.cow.servlet.download.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seven.cow.servlet.download.service.DownloadFileService;
import com.seven.cow.spring.boot.autoconfigure.util.LoggerUtils;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: TODO
 * @author：EDY
 * @date: 2021/11/26 10:26
 * @version: 1.0
 */
public class DefaultDownloadFileServiceImpl implements DownloadFileService {

    @Override
    public byte[] download(String fileKey, String storeAddress) {
        String filePath = (storeAddress + "/" + fileKey);
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            try (FileInputStream inputStream = new FileInputStream(file)) {
                byte[] bytes = StreamUtils.copyToByteArray(inputStream);
                LoggerUtils.info("Download A File Successful,FileKey:" + fileKey + ",Size:" + humanReadable(bytes.length, true) + " !");
                return bytes;
            } catch (Exception ex) {
                LoggerUtils.error("Download A File Failure,FileKey:" + fileKey + "!", ex);
            }
        } else {
            Map<String, String> map = new HashMap<>(1);
            try {
                map.put("message", "fileKey:" + fileKey + " is missing!");
                return new ObjectMapper().writeValueAsBytes(map);
            } catch (JsonProcessingException e) {
                LoggerUtils.error(e.getMessage(), e);
            }
        }
        return new byte[0];
    }

    static String humanReadable(int bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
}
