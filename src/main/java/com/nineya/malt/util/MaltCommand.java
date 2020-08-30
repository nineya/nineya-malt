package com.nineya.malt.util;

import com.nineya.malt.tool.FileTool;
import com.nineya.malt.variety.CommandType;
import com.nineya.malt.variety.FileType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

/**
 * @author linsongwang
 * @date 2020/5/31 12:19
 * 处理控制台命令
 */
public class MaltCommand {
    private static Logger Log = LoggerFactory.getLogger(MaltCommand.class);
    private static Scanner in = new Scanner(System.in);

    // 接受控制台命令
    public static void accept(){
        Log.info("");
        System.out.print("malt>");
        while (in.hasNext()){
            String command = in.next();
            switch (CommandType.value(command)){
                case HELP:{
                    help();
                    break;
                }
                case COMPRESS:{
                    compress();
                    break;
                }
                case CONFIG:{
                    config();
                    break;
                }
                case PWD:{
                    pwd();
                    break;
                }
                case EXIT:{
                    exit();
                    break;
                }
                default:{
                    Log.error("未找到命令：" + command +"，输入“help”查看帮助。");
                }
            }
            Log.info("");
            System.out.print("malt>");
        }
    }

    // 压缩
    private static void compress(){
        String path = in.next();
        // 不是绝对路径
        if (!FileTool.isAbsolutePath(path)){
            path = MaltConfig.JAR_PATH + "/" + path;
        }
        if (!FileTool.fileExists(path)){
            Log.error("错误：路径“" + path + "”不存在文件或目录！");
            return;
        }
        if (FileTool.isFile(path)){     //是文件
            String suffix = FileTool.nameSuffix(path);
            if (suffix == null){     //该文件没有后缀
                Log.error("错误：路径“" + path + "”文件没有后缀名！");
            } else {
                FileType fileType = FileType.value(suffix);
                // 该文件在支持的压缩范围之内
                if (fileType != FileType.OTHER){
                    compressFile(fileType, path, FileTool.createPeerFile(path));
                } else {
                    Log.error("错误：路径“" + path + "”目前不支持压缩“"+suffix+"”格式文件！");
                }
            }
        } else {    // 用户输入的是一个目录
            List<FileType> fileTypes = MaltConfig.getCfg().getFileTypes();
            boolean copy = MaltConfig.getCfg().isNoCompressCopy();
            List<String> files = FileTool.recursiveFiles(path);     //取得文件
            int pathNum = path.length();
            // 创建同级目录对应的文件夹
            String newPath = FileTool.createPeerDirectory(path);
            for (String filePath : files){
                String suffix = FileTool.nameSuffix(filePath);
                if (suffix == null){     //该文件没有后缀
                    Log.error("错误：路径“" + filePath + "”文件没有后缀名！");
                } else {
                    FileType fileType = FileType.value(suffix);
                    // 该文件在支持的压缩范围之内
                    if (fileTypes.contains(fileType)){
                        compressFile(fileType, filePath, newPath + filePath.substring(pathNum));
                    } else {
                        if (copy){
                            //TODO 未完成
                        }
                    }
                }
            }
        }
    }

    // 查看配置信息
    private static void config(){
        Log.info("当前配置信息：");
        Log.info(MaltConsole.print(MaltConfig.asMap()));
    }

    /**
     * 压缩文件
     * @param fileType 文件类型
     * @param path 文件路径
     * @param newPath 压缩后新文件路径
     */
    private static void compressFile(FileType fileType, String path, String newPath) {
        Log.info("压缩文件："+path);
        switch (fileType){
            case HTML:{
                MaltConfig.getCfg().getEnter().compressHtml();
                break;
            }
        }
        Log.info("压缩后保存路径："+newPath);
    }

    // 退出系统
    public static void exit(){
        Log.info("再见，欢迎再次使用！");
        System.exit(0);
    }

    // 输出程序路径
    public static void pwd(){
        Log.info("程序路径：" + MaltConfig.JAR_PATH);
    }

    // 帮助
    public static void help(){
        CommandType[] commandTypes = CommandType.values();
        String[][] list = new String[commandTypes.length + 1][3];
        list[0][0] = "命令";
        list[0][1] = "示例";
        list[0][2] = "说明";
        for (int i = 0; i < commandTypes.length; i++){
            list[i + 1][0] = commandTypes[i].name();
            list[i + 1][1] = commandTypes[i].getExample();
            list[i + 1][2] = commandTypes[i].getDescription();
        }
        Log.info("麦芽命令列表：\n" + MaltConsole.printList(list));
    }
}
