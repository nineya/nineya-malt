package com.nineya.malt.internal;

/**
 * @author linsongwang
 * @date 2020/5/31 10:11
 * 压缩入口类接口
 */
public interface Enter {
    String compressHtml(String code);
    String compressJs(String code);
    String compressCss(String code);
}
