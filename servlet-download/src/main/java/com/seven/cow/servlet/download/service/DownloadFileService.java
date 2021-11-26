package com.seven.cow.servlet.download.service;

public interface DownloadFileService {

    byte[] download(String fileKey, String storeAddress);

}
