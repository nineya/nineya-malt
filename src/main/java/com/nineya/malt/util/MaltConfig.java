package com.nineya.malt.util;

import com.nineya.malt.compress.BaseEnter;
import com.nineya.malt.compress.Enter;
import com.nineya.malt.entity.Configuration;
import com.nineya.malt.exception.MaltFileException;
import com.nineya.malt.tool.FileTool;
import com.nineya.malt.variety.FileType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author linsongwang
 * @date 2020/5/31 10:09
 */
public class MaltConfig {
    private static Logger Log = LoggerFactory.getLogger(MaltConfig.class);
    private static MaltConfig config = null;
    // 取得jar所在path
    public static String JAR_PATH = FileTool.jarWholePath();
    // 配置压缩文件类型
    private List<FileType> fileTypes;
    // 是否复制未压缩的文件
    private boolean noCompressCopy;
    // 压缩入口
    private Enter enter;

    private MaltConfig(){
    }

    public static MaltConfig getCfg(){
        if (config==null){
            Log.error("未初始化配置信息！");
        }
        return config;
    }

    // 初始化，再次初始化将覆盖上次配置信息
    public static void init(Configuration configuration){
        Log.info(MaltConsole.getLineNum("malt", "正在载入配置..."));
        config = new MaltConfig();
        config.fileTypes = configuration.getFileTypes();
        Log.info("压缩文件类型：" + config.fileTypes);
        config.noCompressCopy = configuration.isNoCompressCopy();
        Log.info("是否复制不压缩文件到新目录：" + config.noCompressCopy);
        config.enter = new BaseEnter();
        Log.info(MaltConsole.getLineNum("malt", "配置信息载入成功！"));
    }

    /**
     * 初始化，传入文件路径
     * @param path
     */
    public static void init(String path){
        Log.info(MaltConsole.getLineNum("malt", "读取配置文件中..."));
        Map map = FileTool.readYaml(path);
        if (map != null){
            Configuration configuration = new Configuration();
            configuration.setFileTypes(loadFileTypes((List<String>) map.get("fileTypes")));
            configuration.setNoCompressCopy(Boolean.parseBoolean(map.get("noCompressCopy").toString()));
            Log.info("配置文件读取成功！");
            init(configuration);
        } else {
            Log.info("正在加载默认配置！");
            init();
        }
    }

    // 初始化，加载默认配置
    public static void  init(){
        Log.info(MaltConsole.getLineNum("malt", "加载默认配置中..."));
        init(defaultConfiguration());
    }

    public List<FileType> getFileTypes() {
        return fileTypes;
    }

    public boolean isNoCompressCopy() {
        return noCompressCopy;
    }

    public Enter getEnter() {
        return enter;
    }

    // 加载需要压缩的文件类型
    private static List<FileType> loadFileTypes(List<String> types){
        List<FileType> fileTypes = new ArrayList<>();
        for (String type : types){
            FileType fileType = FileType.value(type);
            if (fileType != FileType.OTHER){
                fileTypes.add(fileType);
            } else {
                try {
                    throw new MaltFileException("不支持压缩文件类型："+type+"，支持的文件类型有" + FileType.supportType());
                } catch (MaltFileException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileTypes;
    }

    // 加载默认配置
    public static Configuration defaultConfiguration(){
        Configuration configuration = new Configuration();
        List<FileType> fileTypes = new ArrayList<>();
        fileTypes.add(FileType.HTML);
        fileTypes.add(FileType.JS);
        fileTypes.add(FileType.CSS);
        configuration.setFileTypes(fileTypes);
        configuration.setNoCompressCopy(false);
        return configuration;
    }

    // 取得map类型配置信息
    public static Map<String, Object> asMap(){
        Map<String, Object> map = new HashMap<>();
        if (config == null) {
            map.put("错误", "未初始化配置！");
            return map;
        }
        map.put("压缩文件类型", config.getFileTypes());
        map.put("复制未压缩文件到新目录", config.isNoCompressCopy());
        return map;
    }
}
