package com.seven.cow.spring.boot.autoconfigure.entity.file;

import com.seven.cow.spring.boot.autoconfigure.util.DataSizeUtil;

import java.io.Serializable;

public abstract class FileInfo implements Serializable {

    private final int size;

    public FileInfo(int size) {
        this.size = size;
    }

    public abstract String key();

    public final String humanReadableSize() {
        return DataSizeUtil.format(size);
    }

}
