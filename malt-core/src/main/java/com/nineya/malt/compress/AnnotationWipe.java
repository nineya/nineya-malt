package com.nineya.malt.compress;

/**
 * @author linsongwang
 * @date 2020/6/14 3:58
 * 注释清除
 */
public class AnnotationWipe {
    private static final String COMMON_MULTI_LINE = "\\/\\*(.|\\n)*?\\*\\/";
    private static final String SINGLE_LINE = "\\/\\/.*";

    /**
     * 最基础的清除多行注释的方法
     * @param code 代码文件内容
     * @return 压缩后的内容
     */
    public static String commonMultiLine(String code){
        return code.replaceAll(COMMON_MULTI_LINE, "");
    }

    /**
     * 压缩单行注释
     * @param code 代码文件内容
     * @return 压缩后的内容
     */
    public static String singleList(String code){
        return code.replaceAll(SINGLE_LINE, "");
    }
}
