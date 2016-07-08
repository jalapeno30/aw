package com.ilts.anywhere.response;

public class ValidSessionResponse extends Response {

	ValidSessionResponse() {
		super(ResponseType.SUCCESSVALIDSESSION);
		construct();
	}

	@Override
	protected void construct() {
		System.out.println("Session is valid");
		setStatus("success");
	}

}
