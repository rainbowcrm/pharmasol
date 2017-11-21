package com.primus.common;

import org.apache.log4j.LogManager;

public class Logger {

    public  static void logDebug(String message , Class currentClass)
    {
        LogManager.getLogger(currentClass).debug(message);
    }

    public  static void logTrace(String message , Class currentClass)
    {
        LogManager.getLogger(currentClass).trace(message);
    }

    public  static void logException(String message , Class currentClass , Exception ex)
    {
        LogManager.getLogger(currentClass).error(message,ex);
    }
}
