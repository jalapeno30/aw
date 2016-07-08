package com.ilts.anywhere.response.http;

public class CreatedHttpResponse extends HttpResponse {
	
	public CreatedHttpResponse(Object data) {
		super(HttpResponseType.CREATED);
		construct();
		this.setData(data);
	}

	@Override
	protected void construct() {
		// TODO Auto-generated method stub

	}

}
