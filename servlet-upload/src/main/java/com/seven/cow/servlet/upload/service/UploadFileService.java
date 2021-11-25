package com.seven.cow.servlet.upload.service;

import com.seven.cow.servlet.upload.service.impl.FileInfo;

public interface UploadFileService {

    FileInfo upload(byte[] content, String storeAddress);

    Object writeResponseInfo(FileInfo fileInfo);

}
