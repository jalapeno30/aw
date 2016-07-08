package com.ilts.anywhere.response.http;

public class MovedPermanentlyHttpResponse extends HttpResponse {
	
	public MovedPermanentlyHttpResponse(Object data) {
		super(HttpResponseType.MOVEDPERMANENTLY);
		construct();
		this.setData(data);
	}
	
	@Override
	protected void construct() {
		// TODO Auto-generated method stub

	}

}
