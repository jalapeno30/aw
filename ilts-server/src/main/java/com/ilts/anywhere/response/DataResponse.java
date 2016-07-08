package com.ilts.anywhere.response;

public class DataResponse extends Response {

	DataResponse() {
		super(ResponseType.SUCCESSDATA);
		construct();
	}
	
	private Object data;

	@Override
	protected void construct() {
		setStatus("success");
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}


}
