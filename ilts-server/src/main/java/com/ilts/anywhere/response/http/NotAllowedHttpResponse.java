package com.ilts.anywhere.response.http;

public class NotAllowedHttpResponse extends HttpResponse {

	public NotAllowedHttpResponse(Object data) {
		super(HttpResponseType.NOTALLOWED);
		construct();
		this.setData(data);
	}
	
	@Override
	protected void construct() {
		// TODO Auto-generated method stub

	}

}
