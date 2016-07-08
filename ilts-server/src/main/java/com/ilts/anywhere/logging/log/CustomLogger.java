/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: CustomLogger.java
 */
package com.ilts.anywhere.logging.log;

import com.ilts.anywhere.logging.ConfigMgr;
import com.ilts.anywhere.logging.utils.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

/**
 *
 * @author jnguyen
 */
public final class CustomLogger {
    // Logger types
    public enum LogType
    {
        SEVERE,
        WARNING,
        INFO,
        DEBUG,
        ALL,
        OFF
    }

    // Singleton implementation
    static CustomLogger _instance = null;
    static final Object _padlock = new Object();

    public static CustomLogger getInstance(ConfigMgr configMgr) throws Exception {
        synchronized (_padlock)
        {
            {
                if (_instance == null)
                {
                    _instance = new CustomLogger(configMgr);
                }

                return _instance;
            }
        }
    }
    public static CustomLogger getInstance() throws Exception {
        synchronized (_padlock)
        {
            {
                if (_instance == null)
                {
                    _instance = new CustomLogger();
                }

                return _instance;
            }
        }
    }

    // Configuration manager and file
    private ConfigMgr _configMgr = null;
    private String _configurationFile = "";
    private String _logFile = "";
    private Boolean _detailedLogInfo = false;

    public void setConfigurationManager(ConfigMgr configMgr) {
        if (configMgr != null) {
            _configMgr = configMgr;
        }
    }
    public void setConfigurationFile(String configFile) {
        if (StringUtils.isNullOrEmpty(configFile) == false) {
            _configurationFile = configFile;
        }
    }

    // Minimum file size is 1MB
    private long _minFileSize = 1024000;
    private Vector<String> _configuredLogTypes = new Vector<String>();

    /**
     * Function - getConfiguredLogTypes
     * @exception throws Exception
     * @return Vector (array) of LogTypes
     */
    public Vector<String> getConfiguredLogTypes() throws Exception {
        return _configuredLogTypes;
    }

    /**
     * Function - setup
     * @exception throws Exception
     */
    private void setup() throws Exception {
        if (_configMgr != null) {
            if (StringUtils.isNullOrEmpty(_configMgr.getConfigParamValue("LogTypes")) == false) {
                String[] tokens = _configMgr.getConfigParamValue("LogTypes").split("\\|");

                for (int idx = 0; idx < tokens.length; idx++)
                {
                    _configuredLogTypes.add(tokens[idx]);
                }
            } else {
                _configuredLogTypes.add(LogType.DEBUG.name());
            }

            if (StringUtils.isNullOrEmpty(_configMgr.getConfigParamValue("FileLogPath")) == false) {
                _logFile = _configMgr.getConfigParamValue("FileLogPath");

                try {
                    // Make sure that the directory exists
                    File fileDirObj = new File((new File(_logFile)).getParent());

                    if (fileDirObj.exists() == false) {
                        fileDirObj.mkdir();
                    }
                } catch (Exception ex) {}
            } else {
                _logFile = "";
            }

            if (StringUtils.isNullOrEmpty(_configMgr.getConfigParamValue("DetailedLogInfo")) == false) {
                _detailedLogInfo = Boolean.parseBoolean(_configMgr.getConfigParamValue("DetailedLogInfo"));
            } else {
                _detailedLogInfo = false;
            }
        }
    }

    /**
     * Constructor - CustomLogger
     * @param ConfigMgr object
     * @exception throws Exception
     */
    public CustomLogger(ConfigMgr configMgr) throws Exception {
        setConfigurationManager(configMgr);

        setup();
    }
    /**
     * Constructor - CustomLogger
     * @exception throws Exception
     */
    public CustomLogger() throws Exception {
        setup();
    }

    /**
     * Function - refresh
     */
    public void refresh() {
        // Might need to use "synchronized" here
        _configuredLogTypes.clear();
        _logFile = "";

        try {
            setup();
        } catch (Exception ex) {
            // TODO: What/how to handle
        }
    }

    /**
     * Function - resizeLogFile
     * @param fileName
     */
    private void resizeLogFile(String fileName) {
        long nFileSize = 0;
        // Set default max file size
        long maxFileSize = _minFileSize;
        File fileObj = null;
        BufferedWriter buffWriter = null;

        if (_configMgr != null) {
            // Get updated file size value
            if (StringUtils.isNullOrEmpty(_configMgr.getConfigParamValue("LogFileSize")) == false) {
                nFileSize = Integer.parseInt(_configMgr.getConfigParamValue("LogFileSize"));
            }
        }

        // Update the max file size
        if (nFileSize > maxFileSize)
            maxFileSize = nFileSize;

        // Resizing log file
        try {
            fileObj = new File(fileName);

            if (fileObj.exists() == true) {
                if (fileObj.length() > maxFileSize) {
                    StringBuilder startMsg = new StringBuilder();

                    try
                    {
                        // Create a new filename
                        String newFileName = String.format("%s%s%s_%s%s", fileObj.getParent(), System.getProperty("file.separator"),
                                                                          StringUtils.getSubstring(fileObj.getName(), "\\.", 1),
                                                                          new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()),
                                                                          ((StringUtils.getSubstring(fileObj.getName(), "\\.", 2) != "") ?
                                                                            "." + StringUtils.getSubstring(fileObj.getName(), "\\.", 2) :
                                                                            ""));

                        // Save for logging/historical purpose
                        fileObj.renameTo(new File(newFileName));

                        startMsg.append("Previous log file - '" + newFileName + "'");
                    }
                    catch (Exception ex)
                    {
                        startMsg.append("Error: Encountered exception during creating/saving log file due to - " + ex.toString());
                    }

                    buffWriter = new BufferedWriter(new FileWriter(fileName, false));
                    buffWriter.write(startMsg.toString());
                    buffWriter.newLine();

                    buffWriter.close();
                }
            }
        } catch (Exception ex) {
            // TODO: What/how to handle
            System.out.println("resizeLogFile - " + ex.toString());
        }
    }

    /**
     * Function - isLoggingType
     * @param logIfType of LogType
     * @return status of checking of input logIfType (true or false)
     */
    private Boolean isLoggingType(LogType logIfType)
    {
        if (_configuredLogTypes.contains(LogType.OFF.name()) == true) {
            return false;
        } else {
            // Default: "Error" and "All"
            // Otherwise: logIfType must matches what in configuredLogTypes
            return (logIfType == LogType.SEVERE || logIfType == LogType.WARNING ||
                    _configuredLogTypes.contains(LogType.ALL.name()) ||
                    _configuredLogTypes.contains(logIfType.name()));
        }
    }

    /**
     * Function - getConfigParamValue
     * @param keyName key name to get the string value out of a setup | config filename
     */
    public String getConfigParamValue(String keyName) {
        if (_configMgr != null) {
            return _configMgr.getConfigParamValue(keyName);
        } else {
            return "";
        }
    }

    /**
     * Function - logException
     * @param logIfType of LogType
     * @param includeTimeStamp for current timestamp
     * @param titleMsg title message for exception
     * @param exObj Exception object to be logged
     */
    public synchronized void logException(LogType logIfType, Boolean includeTimeStamp, String titleMsg, Exception exObj) {
        StringBuilder strBld = new StringBuilder();
        StackTraceElement[] stckElems = exObj.getStackTrace();

        strBld.append(((exObj.getMessage() == null) ? "<No Message>" : exObj.getMessage())); strBld.append("\n");
        for (int idx = 0; idx < stckElems.length; idx++) {
            strBld.append(stckElems[idx].toString()); strBld.append("\n");
        }

        // Logging Stack Trace
        log(logIfType, includeTimeStamp, titleMsg + " - " + strBld.toString());
    }

    /**
     * Function - logException
     * @param fileToLog dynamic log file path
     * @param logIfType of LogType
     * @param includeTimeStamp for current timestamp
     * @param titleMsg title message for exception
     * @param exObj Exception object to be logged
     */
    public synchronized void logException(String fileToLog, LogType logIfType, Boolean includeTimeStamp, String titleMsg, Exception exObj) {
        StringBuilder strBld = new StringBuilder();
        StackTraceElement[] stckElems = exObj.getStackTrace();

        strBld.append(((exObj.getMessage() == null) ? "<No Message>" : exObj.getMessage())); strBld.append("\n");
        for (int idx = 0; idx < stckElems.length; idx++) {
            strBld.append(stckElems[idx].toString()); strBld.append("\n");
        }

        // Logging Stack Trace
        log(fileToLog, logIfType, includeTimeStamp, titleMsg + " - " + strBld.toString());
    }

    /**
     * Function - log
     * @param logIfType of LogType
     * @param includeTimeStamp for current timestamp
     * @param text of string to be logged
     */
    public synchronized void log(LogType logIfType, Boolean includeTimeStamp, String text) {
//        System.out.println("log - [" + logIfType.name() + ", " + includeTimeStamp + "]");
        if (isLoggingType(logIfType) == true) {
//            System.out.println("_logFile - [" + _logFile + "]");
            if (StringUtils.isNullOrEmpty(_logFile) == false) {
                File fileObj = null;
                BufferedWriter buffWriter = null;
                StringBuilder formattedMsg = new StringBuilder();
//                StringBuilder strBld = new StringBuilder();

                try {
                    // Make sure that the directory exists
                    File fileDirObj = new File((new File(_logFile)).getParent());

                    if (fileDirObj.exists() == false) {
                        fileDirObj.mkdir();
                    }

                    // Check for log file size
                    resizeLogFile(_logFile);

                    buffWriter = new BufferedWriter(new FileWriter(_logFile, true));

                    formattedMsg.append(String.format("[%7s]", logIfType.name()));
                    if (includeTimeStamp == true) {
                        formattedMsg.append(String.format(" %s", new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime())));
                    }
                    if (_detailedLogInfo == true) {
                        Throwable t = new Throwable();
//                        StackTraceElement[] stckElems = t.getStackTrace();
                        StackTraceElement stckElem = t.getStackTrace()[2];

//                        for (int idx = 0; idx < stckElems.length; idx++) {
//                            strBld.append(stckElems[idx].toString()); strBld.append("\n");
//                        }

                        formattedMsg.append(String.format(" (%s,%s,%s)", stckElem.getClassName(), stckElem.getMethodName(), stckElem.getLineNumber()));
                    }

                    buffWriter.write(String.format("%s::%s", formattedMsg.toString(), text));
                    buffWriter.newLine();
//                    buffWriter.write(strBld.toString());
//                    buffWriter.newLine();
                    buffWriter.flush();
                    buffWriter.close();
                } catch (Exception ex) {
                    // TODO: What/how to handle
                    System.out.println("log - " + ex.toString());
                }
            }
        }
    }

    /**
     * Function - log
     * @param fileToLog dynamic log file path
     * @param logIfType of LogType
     * @param includeTimeStamp for current timestamp
     * @param text of string to be logged
     */
    public synchronized void log(String fileToLog, LogType logIfType, Boolean includeTimeStamp, String text) {
        if (isLoggingType(logIfType) == true) {
            if (StringUtils.isNullOrEmpty(fileToLog) == false) {
                File fileObj = null;
                BufferedWriter buffWriter = null;
                StringBuilder formattedMsg = new StringBuilder();

                try {
                    // Make sure that the directory exists
                    File fileDirObj = new File((new File(fileToLog)).getParent());

                    if (fileDirObj.exists() == false) {
                        fileDirObj.mkdir();
                    }

                    // Check for log file size
                    resizeLogFile(fileToLog);

                    buffWriter = new BufferedWriter(new FileWriter(fileToLog, true));

                    formattedMsg.append(String.format("[%7s]", logIfType.name()));
                    if (includeTimeStamp == true) {
                        formattedMsg.append(String.format(" %s", new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime())));
                    }
                    if (_detailedLogInfo == true) {
                        Throwable t = new Throwable();
                        StackTraceElement stckElem = t.getStackTrace()[2];

                        formattedMsg.append(String.format(" (%s,%s,%s)", stckElem.getClassName(), stckElem.getMethodName(), stckElem.getLineNumber()));
                    }

                    buffWriter.write(String.format("%s::%s", formattedMsg.toString(), text));
                    buffWriter.newLine();

                    buffWriter.flush();
                    buffWriter.close();
                } catch (Exception ex) {
                    // TODO: What/how to handle
                    System.out.println("log - " + ex.toString());
                }
            }
        }
    }
}