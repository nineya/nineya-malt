package com.nineya.malt.compress;

import com.nineya.malt.variety.RegularExpression;

/**
 * @author linsongwang
 * @date 2020/6/14 3:58
 * 注释清除
 */
public class AnnotationWipe {

    /**
     * 输入代码格式，返回去除
     * @param content 未清除注释的css
     * @return 处理后的css
     */
    public String css(String content){
        return content.replaceAll(RegularExpression.css_multi_line_annotation.getContent(), "");
    }
}
