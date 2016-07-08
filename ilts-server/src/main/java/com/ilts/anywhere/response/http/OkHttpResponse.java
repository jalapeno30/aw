package com.ilts.anywhere.response.http;

public class OkHttpResponse extends HttpResponse {
	
	public OkHttpResponse(Object data) {
		super(HttpResponseType.OK);
		construct();
		this.setData(data);
	}
        public OkHttpResponse(Object data,String cutsomMessage) {
		super(HttpResponseType.OK);
		construct();
		this.setData(data,cutsomMessage);
	}
	
	@Override
	protected void construct() {
		// TODO Auto-generated method stub

	}

}
