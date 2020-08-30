package com.nineya.malt.util;

import com.nineya.malt.entity.Configure;
import com.nineya.malt.tool.FileTool;
import org.apache.commons.cli.*;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * @author linsongwang
 * @date 2020/8/30
 */
public class ParameterUtil {

    private final static Options OPTIONS  = buildOptions();

    private final static String FILE_ARG = "f";
    private final static String FILE_LONGARG = "file";
    private final static String FILE_DESC = "yaml配置文件所在路径";

    private final static String PATH_ARG = "p";
    private final static String PATH_LONGARG = "path";
    private final static String PATH_DESC = "需要进行压缩的目录或者文件所在路径";

    /**
     * 创建加载命令行参数的Options
     * @return Options
     */
    public static Options buildOptions(){
        Options options = new Options();
        options.addOption(FILE_ARG, FILE_LONGARG, true, FILE_DESC);
        options.addOption(PATH_ARG, PATH_LONGARG, true, PATH_DESC);
        return options;
    }

    /**
     * 加载启动时参数
     * @param args 参数列表
     * @throws ParseException 读取参数异常
     */
    public static void parseArgs(String[] args) throws ParseException {
        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(OPTIONS, args);
        if (cmd.hasOption(FILE_ARG)){
            String filePath = cmd.getOptionValue(FILE_ARG);
            if (!FileTool.isAbsolutePath(filePath)){
                filePath = FileTool.jarWholePath() + filePath;
            }
            try {
                parseYaml(filePath);
            } catch (FileNotFoundException e) {
                LoggerUtil.getErrorLogger().error("yaml配置文件不存在："+filePath, e);
            }
            Configure.setFile(filePath);
        }
        if (cmd.hasOption(PATH_ARG)){
            String path = cmd.getOptionValue(PATH_ARG);
            if (!FileTool.isAbsolutePath(path)){
                path = FileTool.jarWholePath() + path;
            }
            Configure.setPath(path);
        }
    }

    /**
     * 加载yaml配置文件
     * @param filePath 配置文件链接
     */
    public static void parseYaml(String filePath) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        Map<String, String> map = yaml.loadAs(new FileInputStream(filePath), Map.class);
        for (Map.Entry<String, String> entry : map.entrySet()){
            if (entry.getKey().equals(PATH_LONGARG)){
                String path = entry.getValue();
                if (!FileTool.isAbsolutePath(path)){
                    path = FileTool.jarWholePath() + path;
                }
                Configure.setPath(path);
            }
        }
    }
}
