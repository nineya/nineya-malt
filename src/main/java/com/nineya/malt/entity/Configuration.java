package com.nineya.malt.entity;

import com.nineya.malt.variety.FileType;

import java.util.List;

/**
 * @author linsongwang
 * @date 2020/6/1 10:46
 */
public class Configuration {
    // 配置压缩文件类型
    private List<FileType> fileTypes;
    // 是否复制未压缩的文件
    private boolean noCompressCopy;

    public Configuration() {
    }

    public Configuration(List<FileType> fileTypes, boolean noCompressCopy) {
        this.fileTypes = fileTypes;
        this.noCompressCopy = noCompressCopy;
    }

    public List<FileType> getFileTypes() {
        return fileTypes;
    }

    public void setFileTypes(List<FileType> fileTypes) {
        this.fileTypes = fileTypes;
    }

    public boolean isNoCompressCopy() {
        return noCompressCopy;
    }

    public void setNoCompressCopy(boolean noCompressCopy) {
        this.noCompressCopy = noCompressCopy;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "fileTypes=" + fileTypes +
                ", noCompressCopy=" + noCompressCopy +
                '}';
    }
}
