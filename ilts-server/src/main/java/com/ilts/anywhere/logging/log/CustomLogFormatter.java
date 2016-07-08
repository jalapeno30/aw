/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: CustomLogFormatter.java
 */
package com.ilts.anywhere.logging.log;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 *
 * @author jnguyen
 */
public class CustomLogFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        return "[" +
               record.getLevel().getName() +
               "] " +
               record.getSourceClassName() + "::" +
               record.getSourceMethodName() + "::" +
               new Date(record.getMillis()) + "::" +
               record.getMessage() + "\n";
    }
}