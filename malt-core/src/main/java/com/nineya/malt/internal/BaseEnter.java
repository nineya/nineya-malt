package com.nineya.malt.internal;

/**
 * @author linsongwang
 * @date 2020/5/31 22:00
 */
public class BaseEnter implements Enter {
    // 压缩 html
    @Override
    public String compressHtml(String code) {
        return code;
    }

    // 压缩js
    @Override
    public String compressJs(String code) {
        return code;
    }

    // 压缩css
    @Override
    public String compressCss(String code) {
        return code;
    }
}
