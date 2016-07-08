package com.ilts.anywhere.response;

public class ChangeStatusResponse extends Response {

	ChangeStatusResponse() {
		super(ResponseType.SUCCESSCHANGESTATUS);
		construct();
	}

	@Override
	protected void construct() {
		System.out.println("Changing status");
		setStatus("success");
	}

}
