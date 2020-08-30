package com.nineya.malt.console;

import com.nineya.malt.util.LoggerUtil;
import com.nineya.slog.Logger;

import java.util.Scanner;

/**
 * @author linsongwang
 * @date 2020/8/30
 */
public class Console {
    private static final Scanner in = new Scanner(System.in);
    private static Logger logger = LoggerUtil.getConsoleLogger();
    private static final String HINT = "malt>";

    public static void awaitInput(){
        System.out.print(HINT);
        while (in.hasNext()){
            String[] args = printArgs(in.nextLine());
            Command.parseCommand(args);
        }
    }

    public static String[] printArgs(String args){
        logger.debug(HINT + args);
        return args.split("\\s+");
    }

    public static void printResult(String result){
        logger.debug(result);
        System.out.print("malt>");
    }
}
