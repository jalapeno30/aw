package com.ilts.anywhere.response;

import com.ilts.anywhere.logging.log.CustomLogger;
import com.ilts.anywhere.utils.Logger;

public class InvalidSessionErrorResponse extends Response {

	InvalidSessionErrorResponse() {
		super(ResponseType.ERRORINVALIDSESSION);
		construct();
	}

	@Override
	protected void construct() {
                    Logger.logMsg(CustomLogger.LogType.DEBUG, Boolean.TRUE, "ErrorResponse for placing Order: Making error response and set Status to 'error'");

		System.out.println("Session is invalid");
		setStatus("error");
	}

}
