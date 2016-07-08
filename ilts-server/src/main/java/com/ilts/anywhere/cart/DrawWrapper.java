package com.ilts.anywhere.cart;

import java.math.BigDecimal;

public class DrawWrapper {

	private String id;
	private String gameId;
	private BigDecimal jackpot;
	private String date;
	private String day;
	private String code;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
	public BigDecimal getJackpot() {
		return jackpot;
	}
	public void setJackpot(BigDecimal jackpot) {
		this.jackpot = jackpot;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
