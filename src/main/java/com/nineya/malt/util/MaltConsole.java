package com.nineya.malt.util;

import com.nineya.malt.tool.StringTool;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author linsongwang
 * @date 2020/5/31 12:20
 */
public class MaltConsole {
    // 执行序号
    private static Map<String, Integer> lineNums = new HashMap<>();

    /**
     * 打印表格
     * @param list 需要打印的表格
     * @return 打印字符串
     */
    public static String printList(String[][] list){
        Font font = new Font("宋体", Font.BOLD, 12);
        // 取得列数
        int colNum = list[0].length;
        // 取得行数
        int rowNum = list.length;
        int[] fontSizes = new int[colNum];
        for (int i = 0; i< colNum; i++) {
            for (int j = 0; j < rowNum; j++) {
                // 因为制表符“═”长度为6，与空格一样长，开头多一个，尾部多一个，防止余数被省略问题
                int size = StringTool.conloseFontSize(font, list[j][i])/6 + 2;
                if (size > fontSizes[i]){
                    fontSizes[i] = size;
                }
            }
        }
        StringBuffer sb = new StringBuffer();
        StringBuffer headSb1 = new StringBuffer("╔");
        StringBuffer headSb2 = new StringBuffer("╠");
        StringBuffer floorSb = new StringBuffer("╚");
        for (int i = 0; i < colNum; i++){
            if (fontSizes[i] > sb.length()){
                for (int j = sb.length(); j < fontSizes[i]; j++){
                    sb.append("═");
                }
            }
            String border = sb.substring(0, fontSizes[i]);
            headSb1.append(border + "╦");
            headSb2.append(border + "╬");
            floorSb.append(border + "╩");
        }
        headSb1.setCharAt(headSb1.length()-1,'╗');
        headSb2.setCharAt(headSb2.length()-1,'╣');
        floorSb.setCharAt(floorSb.length()-1,'╝');
        sb = new StringBuffer();
        sb.append(headSb1.toString() + "\n║");
        for (int i = 0; i<colNum; i++){
            sb.append(" ");
            if (list[0][i]!=null){
                sb.append(list[0][i]);
            }
            int num = fontSizes[i] - StringTool.conloseFontSize(font, list[0][i])/6 - 1;
            for (int j = 0; j < num; j++){
                sb.append(" ");
            }
            sb.append("║");
        }
        sb.append("\n" + headSb2.toString());
        for (int i = 1; i < rowNum; i++){
            sb.append("\n║");
            for (int j = 0; j < colNum; j++){
                sb.append(" ");
                if (list[i][j]!=null){
                    sb.append(list[i][j]);
                }
                int num = fontSizes[j] - StringTool.conloseFontSize(font, list[i][j])/6 - 1;
                for (int k = 0; k < num; k++){
                    sb.append(" ");
                }
                sb.append("║");
            }
        }
        sb.append("\n" + floorSb.toString());
        return sb.toString();
    }

    /**
     * 打印这个对象
     * @param object
     * @return 输出打印的字符
     */
    public static String print(Object object){
        if (object instanceof List){
            return print((List)object, "");
        } else if (object instanceof Map){
            return print((Map)object, "");
        } else {
            return print(object);
        }
    }

    /**
     * 打印列表
     * @param list 要打印的列表
     * @param prefix 前缀空格
     * @return 打印字符串
     */
    public static String print(List list, String prefix){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i<list.size(); i++){
            sb.append(prefix + i + ":");
            Object object = list.get(i);
            if (object instanceof List){
                sb.append("\n" + print((List)object, prefix + "  "));
            } else if (object instanceof Map){
                sb.append("\n" + print((Map)object, prefix + "  "));
            } else {
                sb.append(" " + object + "\n");
            }
        }
        return sb.toString();
    }

    /**
     * 打印map对象
     * @param map 要打印的对象
     * @param prefix 前缀空格
     * @return 打印的字符串
     */
    public static String print(Map<String, Object> map, String prefix){
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof List){
                sb.append(prefix + entry.getKey() + ":\n");
                sb.append(print((List)value, prefix+"  "));
            } else if (value instanceof Map){
                sb.append(prefix + entry.getKey() + ":\n");
                sb.append(print((Map)value, prefix+"  "));
            } else {
                sb.append(prefix + entry.getKey() +" = " + value + "\n");
            }
        }
        return sb.toString();
    }

    /**
     * 打印标题
     * @param level 标题级别
     * @param title 标题内容
     * @return 返回打印信息
     */
    public static String printTitle(int level, String title){
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<level; i++){
            sb.append("#");
        }
        sb.append(" " + title);
        return sb.toString();
    }

    /**
     * 取得指定行号，如果不存在则添加，默认为1；
     * @param lineName 行名称
     * @return 行号
     */
    public static String getLineNum(String lineName, String content){
        int lineNum = lineNums.getOrDefault(lineName, 1);
        lineNums.put(lineName, lineNum+1);
        return lineNum + ". " + content;
    }

    /**
     * 根据行名称删除行号
     * @param lineName 行名称
     */
    public static void removeLineName(String lineName) {
        lineNums.remove(lineName);
    }
}
