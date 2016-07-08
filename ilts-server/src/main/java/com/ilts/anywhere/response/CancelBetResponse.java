package com.ilts.anywhere.response;

//import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import java.security.*;
import java.math.BigInteger;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class CancelBetResponse extends Response {

	CancelBetResponse() {
		super(ResponseType.SUCCESSCANCELBET);
		construct();
	}

	@Override
	protected void construct() {
		System.out.println("Making cancel bet response");
		setStatus("success");
	}

	

}