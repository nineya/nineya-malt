package com.nineya.malt.compress;

/**
 * @author linsongwang
 * @date 2020/8/28
 */
public class FormatWipe {
    private static final String MULTI_SEMICOLON = ";[;]+";
    private static final String LINE_TRIM = "(^\\s*)|(\\s*$)|(\\s*\\n\\s*)";

    /**
     * 清除多个连续的分号
     * @param code 代码文件内容
     * @return 压缩后的内容
     */
    public static String multiSemicolon(String code){
        return code.replaceAll(MULTI_SEMICOLON, ";");
    }

    /**
     * 清除每一行前后的空白字符串和换行符
     * @param code 代码文件内容
     * @return 压缩后的内容
     */
    public static String lineTrim(String code){
        return code.replaceAll(LINE_TRIM, "");
    }
}
