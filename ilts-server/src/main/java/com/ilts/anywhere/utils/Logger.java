/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: CustomLogger.java
 */
package com.ilts.anywhere.utils;

import com.ilts.anywhere.AppConfig;
import com.ilts.anywhere.logging.ConfigMgr;
import com.ilts.anywhere.logging.log.CustomLogger;
import com.ilts.anywhere.logging.utils.LogUtils;

/**
 *
 * @author jnguyen
 */
public final class Logger {
    private static final ConfigMgr configMgr = new AppConfig().getConfigMgr();

    /**
     * Function - getConfigParamValue
     * @param keyName key name to get the string value out of a setup | config filename
     */
    public static String getConfigParamValue(String keyName) {
        return LogUtils.getConfigParamValue(keyName);
    }

    /**
     * Function - log
     * @param logIfType of LogType
     * @param includeTimeStamp for current timestamp
     * @param text of string to be logged
     */
    public static void logMsg(CustomLogger.LogType logIfType, Boolean includeTimeStamp, String text) {
        // Start logging text
        LogUtils.log(logIfType, includeTimeStamp, text);
    }

    /**
     * Function - logException
     * @param logIfType of LogType
     * @param includeTimeStamp for current timestamp
     * @param exObj Exception object to be logged
     */
    public static void logException(CustomLogger.LogType logIfType, Boolean includeTimeStamp, String titleMsg, Exception exObj) {
        // Start logging titleMsg and exception Object
        LogUtils.logException(logIfType, includeTimeStamp, titleMsg, exObj);
    }
}