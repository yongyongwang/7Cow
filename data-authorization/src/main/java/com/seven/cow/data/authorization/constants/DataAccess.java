package com.seven.cow.data.authorization.constants;

/**
 * 数据访问枚举
 */
public enum DataAccess {

    NONE(0, "NONE"), READ(4, "读"), WRITE(2, "写"), EXECUTE(1, "执行"), READ_WRITE(6, "读+写(4+2)"), READ_EXECUTE(5, "读+执行(4+1)"), WRITE_EXECUTE(3, "写+执行(2+1)"), READ_WRITE_EXECUTE(7, "读+写+执行(4+2+1)");

    private final int code;

    private final String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public boolean canRead() {
        return canRead(this);
    }

    public boolean canWrite() {
        return canWrite(this);
    }

    public boolean canExecute() {
        return canExecute(this);
    }

    DataAccess(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private static boolean canRead(DataAccess dataAccess) {
        return READ.equals(dataAccess)
                || READ_WRITE.equals(dataAccess)
                || READ_EXECUTE.equals(dataAccess)
                || READ_WRITE_EXECUTE.equals(dataAccess);
    }

    private static boolean canWrite(DataAccess dataAccess) {
        return WRITE.equals(dataAccess)
                || READ_WRITE.equals(dataAccess)
                || WRITE_EXECUTE.equals(dataAccess)
                || READ_WRITE_EXECUTE.equals(dataAccess);
    }

    private static boolean canExecute(DataAccess dataAccess) {
        return EXECUTE.equals(dataAccess)
                || READ_EXECUTE.equals(dataAccess)
                || WRITE_EXECUTE.equals(dataAccess)
                || READ_WRITE_EXECUTE.equals(dataAccess);
    }

    public static DataAccess codeOf(int code) {
        switch (code) {
            case 1:
                return EXECUTE;
            case 2:
                return WRITE;
            case 3:
                return WRITE_EXECUTE;
            case 4:
                return READ;
            case 5:
                return READ_EXECUTE;
            case 6:
                return READ_WRITE;
            case 7:
                return READ_WRITE_EXECUTE;
            default:
                return NONE;
        }
    }

}
