package com.ilts.anywhere.response.http;

public class BadRequestHttpResponse extends HttpResponse {
	
	public BadRequestHttpResponse(Object data) {
		super(HttpResponseType.BADREQUEST);
		construct();
		this.setData(data);
	}
	
	@Override
	protected void construct() {
		// TODO Auto-generated method stub

	}

}
