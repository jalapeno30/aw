package com.ilts.anywhere.response;


public class ResponseService {

	public Response errorResponse() {
		
		Response response = (Response)ResponseFactory.makeResponse(ResponseType.ERROR);
		
		return response;
	}
	
	public Response errorResponse(String string) {
		
		Response response = this.errorResponse();
		response.setMessage(string);
		
		return response;
		
	}

	public Response confirmFundingResponse(String redirectUri) {
		
		PaypalConfirmFundingResponse response = (PaypalConfirmFundingResponse)ResponseFactory.makeResponse(ResponseType.CONFIRMFUNDINGPAYPAL);
		response.setRedirectURI(redirectUri);
		
		return response;
	}

}
