package com.ilts.anywhere.response;

import com.ilts.anywhere.logging.log.CustomLogger;
import com.ilts.anywhere.utils.Logger;

public class NewOrderResponse extends Response {

	NewOrderResponse() {
		super(ResponseType.SUCCESSNEWORDER);
		construct();
	}

	@Override
	protected void construct() {
		
             Logger.logMsg(CustomLogger.LogType.INFO, Boolean.TRUE, "OrderPlacedSuccessResponse: Making success response and set Status to 'success' for adding Order ");
            System.out.println("Successfully added order");
		setStatus("success");
	}

}
