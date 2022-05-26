package com.seven.cow.spring.boot.autoconfigure.util;


import java.text.DecimalFormat;

public class DataSizeUtil {

    private static final String[] UNIT_NAMES = new String[]{"B", "KB", "MB", "GB", "TB", "PB", "EB"};

    /**
     * 可读的文件大小<br>
     * 参考 http://stackoverflow.com/questions/3263892/format-file-size-as-mb-gb-etc
     *
     * @param size Long类型大小
     * @return 大小
     */
    public static String format(long size) {
        if (size <= 0) {
            return "0";
        }
        int digitGroups = Math.min(UNIT_NAMES.length - 1, (int) (Math.log10(size) / Math.log10(1024)));
        return new DecimalFormat("#,##0.##")
                .format(size / Math.pow(1024, digitGroups)) + " " + UNIT_NAMES[digitGroups];
    }

}
