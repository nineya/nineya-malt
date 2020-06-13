package com.nineya.malt.tool;

import java.awt.*;

/**
 * @author linsongwang
 * @date 2020/5/31 18:00
 */
public class StringTool {
    /**
     * 取得文字在控制台的长度
     * @param font 文字样式
     * @param content 文字内容
     * @return 文字长度
     */
    public static int conloseFontSize(Font font, String content){
        if (content == null) {
            return 0;
        }
        return sun.font.FontDesignMetrics.getMetrics(font).stringWidth(content);
    }
}
