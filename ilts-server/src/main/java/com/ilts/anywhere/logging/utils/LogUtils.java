/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: LogUtils.java
 */
package com.ilts.anywhere.logging.utils;

import com.ilts.anywhere.logging.ConfigMgr;
import com.ilts.anywhere.logging.log.CustomLogger;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.logging.LogManager;

/**
 *
 * @author jnguyen
 */
public class LogUtils {
    private static final CustomLogger _logger = LogUtils.getLogger();

    /**
     * Function - getLogger
     * @return assigned JAVA Logger object
     */
    public static CustomLogger getLogger() {
        try {
            return CustomLogger.getInstance();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Function - readConfiguration
     * @param configFileNamePath
     */
    public static void readConfiguration(String configFileNamePath) {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream(configFileNamePath));
        } catch (SecurityException | IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Function - setupConfigurationMgr
     * @param configMgr
     */
    public static void setupConfigurationMgr(ConfigMgr configMgr) {
        // Setup logging
        LogUtils._logger.setConfigurationManager(configMgr);
        LogUtils._logger.refresh();
    }

    /**
     * Function - getConfigParamValue
     * @param keyName key name to get the string value out of a setup | config filename
     */
    public static String getConfigParamValue(String keyName) {
        return LogUtils._logger.getConfigParamValue(keyName);
    }

    /**
     * Function - log
     * @param logIfType of LogType
     * @param includeTimeStamp for current timestamp
     * @param text of string to be logged
     */
    public static void log(CustomLogger.LogType logIfType, Boolean includeTimeStamp, String text) {
        LogUtils._logger.log(logIfType, includeTimeStamp, text);
    }

    /**
     * Function - log
     * @param fileToLog dynamic log file path
     * @param logIfType of LogType
     * @param includeTimeStamp for current timestamp
     * @param text of string to be logged
     */
    public static void log(String fileToLog, CustomLogger.LogType logIfType, Boolean includeTimeStamp, String text) {
        LogUtils._logger.log(fileToLog, logIfType, includeTimeStamp, text);
    }

    /**
     * Function - logException
     * @param logIfType of LogType
     * @param includeTimeStamp for current timestamp
     * @param exObj Exception object to be logged
     */
    public static void logException(CustomLogger.LogType logIfType, Boolean includeTimeStamp, String titleMsg, Exception exObj) {
        LogUtils._logger.logException(logIfType, includeTimeStamp, titleMsg, exObj);
    }

    /**
     * Function - logException
     * @param fileToLog dynamic log file path
     * @param logIfType of LogType
     * @param includeTimeStamp for current timestamp
     * @param exObj Exception object to be logged
     */
    public static void logException(String fileToLog, CustomLogger.LogType logIfType, Boolean includeTimeStamp, String titleMsg, Exception exObj) {
        LogUtils._logger.logException(fileToLog, logIfType, includeTimeStamp, titleMsg, exObj);
    }
}