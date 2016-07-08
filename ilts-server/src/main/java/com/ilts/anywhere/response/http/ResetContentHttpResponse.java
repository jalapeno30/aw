package com.ilts.anywhere.response.http;

public class ResetContentHttpResponse extends HttpResponse {
	
	public ResetContentHttpResponse(Object data) {
		super(HttpResponseType.RESETCONTENT);
		construct();
		this.setData(data);
	}
	
	@Override
	protected void construct() {
		// TODO Auto-generated method stub

	}

}
