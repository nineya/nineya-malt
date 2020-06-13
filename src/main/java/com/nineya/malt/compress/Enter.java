package com.nineya.malt.compress;

/**
 * @author linsongwang
 * @date 2020/5/31 10:11
 * 压缩入口类接口
 */
public interface Enter {
    String compressHtml();
    String compressJs();
    String compressCss();
}
