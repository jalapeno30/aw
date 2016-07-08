package com.ilts.anywhere.response;

public class ChangeRoleResponse extends Response {

	ChangeRoleResponse() {
		super(ResponseType.SUCCESSCHANGEROLE);
		construct();
	}

	@Override
	protected void construct() {
		System.out.println("Changing role");
		setStatus("success");
	}

}
