package com.nineya.malt;

import com.nineya.malt.console.Console;
import com.nineya.malt.util.LoggerUtil;
import com.nineya.malt.util.ParameterUtil;
import org.apache.commons.cli.ParseException;

import java.io.File;
import java.io.IOException;

/**
 * @author linsongwang
 * @date 2020/8/28
 */
public class MaltMain {
    public static void main(String[] args) {
        try {
            ParameterUtil.parseArgs(args);
        } catch (ParseException e) {
            LoggerUtil.getErrorLogger().error("初始化启动参数失败", e);
            System.exit(0);
        }
        try {
            System.out.println(new File("").getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Console.awaitInput();
    }
}
