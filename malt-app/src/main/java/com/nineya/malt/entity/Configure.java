package com.nineya.malt.entity;

/**
 * @author linsongwang
 * @date 2020/8/30
 */
public class Configure {
    private static String file;
    private static String path;

    private Configure(){ }

    public static String getFile() {
        return file;
    }

    public static void setFile(String file) {
        Configure.file = file;
    }

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        Configure.path = path;
    }
}
