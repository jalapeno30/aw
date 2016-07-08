package com.ilts.anywhere.betting;

public class PaypalOAuthResponse {
	
	private String scope;
	private String access_token;
	private String token_type;
	private String app_id;
	private int expires_in;
	
	public String getScope() {
		return scope;
	}
	public String getAccess_token() {
		return access_token;
	}
	public String getToken_type() {
		return token_type;
	}
	public String getApp_id() {
		return app_id;
	}
	public int getExpires_in() {
		return expires_in;
	}
}
