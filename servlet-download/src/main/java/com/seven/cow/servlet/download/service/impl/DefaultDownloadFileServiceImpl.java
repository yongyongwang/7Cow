package com.seven.cow.servlet.download.service.impl;

import com.seven.cow.servlet.download.service.DownloadFileService;
import com.seven.cow.spring.boot.autoconfigure.util.LoggerUtils;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileInputStream;

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
                byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
                LoggerUtils.info("Download A File Successful,FileKey:" + fileKey + ",Size:" + bytes.length + " !");
                return bytes;
            } catch (Exception ex) {
                LoggerUtils.error("Download A File Failure,FileKey:" + fileKey + "!", ex);
            }
        }
        return new byte[0];
    }
}
