package com.ilts.anywhere.response;

import com.ilts.anywhere.logging.log.CustomLogger;
import com.ilts.anywhere.utils.Logger;

public class RegisterResponse extends Response {
    RegisterResponse() {
        super(ResponseType.SUCCESSREGISTER);
        construct();
    }

    @Override
    protected void construct() {
        Logger.logMsg(CustomLogger.LogType.INFO, Boolean.TRUE, "RegistrationSuccessResponse: Making success response and set Status to 'success' for Registration");

        setStatus("success");
    }
}
