package com.nineya.malt.variety;

/**
 * @author linsongwang
 * @date 2020/5/31 17:44
 */
public enum CommandType {
    HELP("help", "打印帮助信息"),
    COMPRESS("compress <待压缩文件/目录所在路径>", "压缩时指定文件或指定目录下的文件"),
    CONFIG("config", "输出程序配置信息"),
    PWD("pwd", "输出程序路径"),
    EXIT("exit", "退出程序");

    private String example;
    private String description;

    CommandType(String example, String description){
        this.example = example;
        this.description = description;
    }

    public String getExample() {
        return example;
    }

    public String getDescription() {
        return description;
    }

    public static CommandType value(String operate) {
        for(CommandType s : values()) {    //values()方法返回enum实例的数组
            if(operate.equalsIgnoreCase(s.name())){
                return s;
            }
        }
        return HELP;
    }
}
