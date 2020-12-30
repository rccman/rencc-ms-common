package com.rencc.common.util;

import org.slf4j.Logger;

public class LogUtil {

    public static void error(Logger logger, String format, Object... arguments){
        logger.error(format,arguments);
    }

    public static void error(Logger logger, String msg , Throwable t){
        logger.error("exception="+t.getMessage()+",,msg1="+msg + ",,StackTrace=",t);
    }

    public static void error(Logger logger, String error){
        logger.error("exception="+error);
    }

}