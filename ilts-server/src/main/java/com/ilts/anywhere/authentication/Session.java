package com.ilts.anywhere.authentication;

import java.security.*;
import java.math.BigInteger;
import java.util.Date;

public class Session {

	private String token;
	private String userId;
	private Date expiration;
	private boolean deactivated = false;

	public Session(String userId) {

		this.userId = userId;
		this.expiration = new Date(System.currentTimeMillis() + 3600 * 1000);

		try {
			MessageDigest m = MessageDigest.getInstance("SHA-1");
			m.reset();
			String combined = this.userId + this.expiration.toLocaleString();
			m.update(combined.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			String hashtext = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32 chars.
			while(hashtext.length() < 32 ){
			  hashtext = "0"+hashtext;
			}
			this.token = hashtext;
		} catch (Exception e) {

		}
	}

	public Session() {}

	public String getToken() {
		return this.token;
	}

	public String getUserId() {
		return this.userId;
	}

	public Date getExpiration() {
		return this.expiration;
	}

	public boolean isDeactivated() {
		return this.deactivated;
	}

	public void deactivate() {
		this.deactivated = true;
	}

}