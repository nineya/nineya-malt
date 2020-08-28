package com.nineya.malt.exception;

import java.io.IOException;

/**
 * @author linsongwang
 * @date 2020/5/31 10:51
 * 文件异常类
 */
public class MaltFileException extends IOException {
    public MaltFileException(String msg){
        super(msg);
    }
}
