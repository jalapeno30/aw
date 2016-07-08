package com.ilts.anywhere.response.http;

import com.ilts.anywhere.logging.log.CustomLogger;
import com.ilts.anywhere.utils.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AcceptedHttpResponse extends HttpResponse {
	
	public AcceptedHttpResponse(Object data) {
	    super(HttpResponseType.ACCEPTED);
	    construct();
	    this.setData(data);
	}
	
	@Override
	protected void construct() {
	     // TODO Auto-generated method stub
             Logger.logMsg(CustomLogger.LogType.INFO, Boolean.TRUE, "AcceptedLoginResponse: Making success response and set data Status to 'success' for Login");
	}

	@Override
	public ResponseEntity<Object> getResponseEntity() {
	      ResponseEntity<Object> response = new ResponseEntity<Object>(this.getData(), HttpStatus.OK);
	      return response;
	}
	

}
