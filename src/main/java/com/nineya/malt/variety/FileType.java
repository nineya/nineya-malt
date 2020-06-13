package com.nineya.malt.variety;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author linsongwang
 * @date 2020/5/31 10:09
 */
public enum  FileType {
    CSS, HTML, JS, OTHER;

    // String 转FileType
    public static FileType value(String operate) {
        for(FileType s : values()) {    //values()方法返回enum实例的数组
            if(operate.equalsIgnoreCase(s.name())){
                return s;
            }
        }
        return OTHER;
    }

    // 支持的文件格式
    public static List<FileType> supportType(){
        List<FileType> fileTypes = new ArrayList<>();
        for (FileType fileType : values()){
            fileTypes.add(fileType);
        }
        fileTypes.remove(FileType.OTHER);
        return fileTypes;
    }
}
