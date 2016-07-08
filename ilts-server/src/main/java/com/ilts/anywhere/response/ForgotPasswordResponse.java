/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: ForgotPasswordResponse.java
 */
package com.ilts.anywhere.response;

import com.ilts.anywhere.logging.log.CustomLogger;
import com.ilts.anywhere.utils.Logger;

public class ForgotPasswordResponse extends Response {
    ForgotPasswordResponse() {
        super(ResponseType.SUCCESSSENDPASSWORD);
        construct();
    }

    @Override
    protected void construct() {
        Logger.logMsg(CustomLogger.LogType.INFO, Boolean.TRUE, "ForgotPasswordResponse: Making success response and set Status to 'success'");
        
        setStatus("success");
    }
}
