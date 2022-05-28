package com.seven.cow.servlet.upload.service;

import com.seven.cow.spring.boot.autoconfigure.entity.file.FileInfo;

public interface UploadFileService {

    FileInfo upload(byte[] content, String storeAddress);

    byte[] writeResponseInfo(FileInfo fileInfo);

}
