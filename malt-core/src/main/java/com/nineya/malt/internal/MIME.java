package com.nineya.malt.internal;

/**
 * @author linsongwang
 * @date 2020/5/31 10:57
 */
public enum MIME {
    UTF_8("UTF-8"),
    GBK("GBK"),
    OTHER("OTHER");
    private String value;

    private MIME(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static MIME value(String operate) {
        for(MIME s : values()) {    //values()方法返回enum实例的数组
            if(operate.equalsIgnoreCase(s.getValue())){
                return s;
            }
        }
        return OTHER;
    }
}
