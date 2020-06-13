package com.nineya.malt.variety;

/**
 * @author linsongwang
 * @date 2020/6/14 4:18
 */
public enum  RegularExpression {
    css_multi_line_annotation("\\/\\*.*\\*\\/");
    private String content;
    RegularExpression(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }
}
