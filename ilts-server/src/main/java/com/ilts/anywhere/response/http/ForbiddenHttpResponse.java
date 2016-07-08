package com.ilts.anywhere.response.http;

import com.ilts.anywhere.logging.log.CustomLogger;
import com.ilts.anywhere.utils.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ForbiddenHttpResponse extends HttpResponse {
    public ForbiddenHttpResponse(Object data) {
        super(HttpResponseType.FORBIDDEN);
        construct();
        this.setData(data);
    }
    
    @Override
    protected void construct() {
        // TODO Auto-generated method stub
        Logger.logMsg(CustomLogger.LogType.INFO, Boolean.TRUE, "ForbiddenLoginResponse: Forbibben response response and set data Status to 'error' for Login");
    }

    public ResponseEntity<Object> getResponseEntity() {
        if (this.getData() != null) {
            ResponseEntity<Object> response = new ResponseEntity<Object>(this.getData(), HttpStatus.FORBIDDEN);

	    return response;
        } else {
            return super.getResponseEntity();
        }
    }
}
