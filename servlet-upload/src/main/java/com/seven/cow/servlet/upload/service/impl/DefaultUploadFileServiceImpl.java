package com.seven.cow.servlet.upload.service.impl;

import com.seven.cow.servlet.upload.service.UploadFileService;
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
    @Override
    public FileInfo upload(byte[] content, String storeAddress) {
        DefaultFileInfo fileInfo = new DefaultFileInfo(content.length);
        String saveDirectory = storeAddress;
        // Save the dir
        File dir = new File(saveDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // Check saveDirectory is truly a directory
        if (!dir.isDirectory())
            throw new IllegalArgumentException("Not a directory: " + saveDirectory);

        // Check saveDirectory is writable
        if (!dir.canWrite())
            throw new IllegalArgumentException("Not writable: " + saveDirectory);
        String fileKey = fileInfo.key();
        try (FileOutputStream outputStream = new FileOutputStream(saveDirectory + "/" + fileInfo.key())) {
            FileCopyUtils.copy(content, outputStream);
        } catch (IOException e) {
            LoggerUtils.info("Upload A File Failure,FileKey:" + fileKey + ",Size:" + fileInfo.humanReadableSize() + " !", e);
        }
        LoggerUtils.info("Upload A File Successful,FileKey:" + fileKey + ",Size:" + fileInfo.humanReadableSize() + " !");
        return fileInfo;
    }

    @Override
    public Object writeResponseInfo(FileInfo fileInfo) {
        return fileInfo;
    }

    static String humanReadable(int bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    static class DefaultFileInfo extends FileInfo {

        private int size;

        public DefaultFileInfo(int size) {
            this.size = size;
        }

        @Override
        public String key() {
            return String.format("%02X", System.nanoTime());
        }

        public String humanReadableSize() {
            return humanReadable(size, true);
        }
    }
}
