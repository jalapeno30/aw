/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: ForgotPasswordResponse.java
 */
package com.ilts.anywhere.response;

import com.ilts.anywhere.logging.log.CustomLogger;
import com.ilts.anywhere.utils.Logger;

public class ErrorResponse extends Response {
    ErrorResponse() {
        super(ResponseType.ERROR);
        construct();
    }

    @Override
    protected void construct() {
        Logger.logMsg(CustomLogger.LogType.DEBUG, Boolean.TRUE, "ErrorResponse for Registration: Making error response and set Status to 'error'");

        setStatus("error");
    }
}