package com.ilts.anywhere.response;

public class PaypalConfirmFundingResponse extends Response {
	
	private String redirectURI;
	
	PaypalConfirmFundingResponse() {
		super(ResponseType.CONFIRMFUNDINGPAYPAL);
		construct();
	}
	
	@Override
	protected void construct() {
		System.out.println("Connecting to paypal for verification");
		setStatus("success");

	}

	public String getRedirectURI() {
		return redirectURI;
	}

	public void setRedirectURI(String redirectURI) {
		this.redirectURI = redirectURI;
	}

}
