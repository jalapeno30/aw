package com.ilts.anywhere.response;

// import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import java.security.*;
import java.math.BigInteger;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class PlaceBetResponse extends Response {

	public String betConfirmationID;

	PlaceBetResponse() {
		super(ResponseType.SUCCESSBETPLACE);
		construct();
	}

	@Override
	protected void construct() {
		System.out.println("Making bet placing success response");
		setStatus("success");

		final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

		try {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

			MessageDigest m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(sdf.format(cal.getTime()).getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			String hashtext = bigInt.toString(16);
			// Now we need to zero pad it if you actually want the full 32 chars.
			while(hashtext.length() < 32 ){
			  hashtext = "0"+hashtext;
			}
			this.betConfirmationID = hashtext;
			// setBetId(hashtext);

		} catch (Exception e) {

		}
	}

	public String getBetConfirmationID() {
		return this.betConfirmationID;
	}

	

}