package com.ilts.anywhere.response.http;

import com.ilts.anywhere.logging.log.CustomLogger;
import com.ilts.anywhere.utils.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class NotFoundHttpResponse extends HttpResponse {
	
	public NotFoundHttpResponse(Object data) {
		super(HttpResponseType.NOTFOUND);
		construct();
		this.setData(data);
	}
	
	@Override
	protected void construct() {
		// TODO Auto-generated method stub
            Logger.logMsg(CustomLogger.LogType.INFO, Boolean.TRUE, "NotFoundResponse: NotFound response  and set data Status to 'error' for any exceptiong or not found data");

	}
          public ResponseEntity<Object> getResponseEntity() {
        if (this.getData() != null) {
            ResponseEntity<Object> response = new ResponseEntity<Object>(this.getData(), HttpStatus.NOT_FOUND);

	    return response;
        } else {
            return super.getResponseEntity();
        }
    }

}
