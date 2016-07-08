package com.ilts.anywhere.games;

public class Draw {

	public int id;
	public int jackpot;
	public String date;
	public String day;
	public String code;

	public Draw(int drawId, int drawJackpot, String drawDate, String drawDay, String drawCode) {
		id = drawId;
		jackpot = drawJackpot;
		date = drawDate;
		day = drawDay;
		code = drawCode;
	}

}