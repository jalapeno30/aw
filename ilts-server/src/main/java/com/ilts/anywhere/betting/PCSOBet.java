package com.ilts.anywhere.betting;

import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import java.security.*;
import java.math.BigInteger;

public class PCSOBet extends OBet {

	PCSOBet() {
		super(BetType.PCSO);
		construct();
	}

	@Override
	protected void construct() {
		System.out.println("Making pcso bet");
	}

	@Override
	public void initialize() {}

	@Override
	public void initialize(String date) {

      	try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(date.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			String hashtext = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32 chars.
			while(hashtext.length() < 32 ){
			  hashtext = "0"+hashtext;
			}
			setBetId(hashtext);

		} catch (Exception e) {

		}
	}

	@Override
	public void setOrders(Order[] orders) {

	}

}