package com.ilts.anywhere.response.http;

public class UnauthorizedHttpResponse extends HttpResponse {
	
	public UnauthorizedHttpResponse(Object data) {
		super(HttpResponseType.UNAUTHORIZED);
		construct();
		this.setData(data);
	}
	
	@Override
	protected void construct() {
		// TODO Auto-generated method stub

	}

}
