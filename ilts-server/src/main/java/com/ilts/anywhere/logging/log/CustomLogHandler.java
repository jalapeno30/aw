/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: CustomLogHandler.java
 */
package com.ilts.anywhere.logging.log;

import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/**
 *
 * @author jnguyen
 */
public class CustomLogHandler extends StreamHandler {
    @Override
    public void publish(LogRecord record) {
        //add own logic to publish
        super.publish(record);
    }


    @Override
    public void flush() {
        super.flush();
    }


    @Override
    public void close() throws SecurityException {
        super.close();
    }
}