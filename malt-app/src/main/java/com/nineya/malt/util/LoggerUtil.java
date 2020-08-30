package com.nineya.malt.util;


import com.nineya.slog.Logger;

/**
 * @author linsongwang
 * @date 2020/8/30
 * 用于管理日志记录器的类
 */
public class LoggerUtil {
    private final static Logger ERROR_LOGGER = Logger.getLogger("error");
    private final static Logger SYS_LOGGER = Logger.getLogger("sys");
    private final static Logger CONSOLE_LOGGER = Logger.getLogger("console");

    public static Logger getErrorLogger() {
        return ERROR_LOGGER;
    }

    public static Logger getSysLogger(){
        return SYS_LOGGER;
    }

    public static Logger getConsoleLogger() {
        return CONSOLE_LOGGER;
    }
}
