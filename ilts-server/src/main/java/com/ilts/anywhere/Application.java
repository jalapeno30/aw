/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: Application.java
 */
package com.ilts.anywhere;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.ilts.anywhere.logging.log.CustomLogger;
import com.ilts.anywhere.logging.log.CustomLogger.LogType;
import com.ilts.anywhere.utils.Logger;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
    	ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        // Setup and retrieving data logging
        Logger.logMsg(CustomLogger.LogType.INFO, Boolean.TRUE, String.format("FileLogPath=%s", Logger.getConfigParamValue("FileLogPath")));
        Logger.logMsg(CustomLogger.LogType.INFO, Boolean.TRUE, String.format("LogFileSize=%s", Logger.getConfigParamValue("LogFileSize")));
        Logger.logMsg(CustomLogger.LogType.INFO, Boolean.TRUE, String.format("LogTypes=%s", Logger.getConfigParamValue("LogTypes")));
        Logger.logMsg(CustomLogger.LogType.INFO, Boolean.TRUE, String.format("DetailedLogInfo=%s", Logger.getConfigParamValue("DetailedLogInfo")));

        SpringApplication.run(Application.class, args);
    }
}
