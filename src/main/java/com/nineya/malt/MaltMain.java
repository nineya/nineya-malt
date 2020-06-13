package com.nineya.malt;

import com.nineya.malt.exception.MaltFileException;
import com.nineya.malt.tool.FileTool;
import com.nineya.malt.util.Command;
import com.nineya.malt.util.Config;
import com.nineya.malt.util.Console;
import com.nineya.malt.variety.MIME;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

/**
 * @author linsongwang
 * @date 2020/5/31 10:09
 */
public class MaltMain {
    private static Logger Log = LoggerFactory.getLogger(MaltMain.class);

    public static void main(String[] args) {
        Log.info(Console.printTitle(2, "初始化"));
        Config.init(Config.JAR_PATH + "/malt/application.yml");
        Log.info(Console.printTitle(2, "运行日志"));
        Log.info(Console.getLineNum("malt", "欢迎使用Malt，启动成功！"));
        Log.info("您可以在此输入命令进行操作，查看帮助请输“help”。");
        Command.accept();
    }
}
