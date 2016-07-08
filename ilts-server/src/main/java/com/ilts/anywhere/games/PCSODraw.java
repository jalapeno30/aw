package com.ilts.anywhere.games;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class PCSODraw {
	
	private int id;
	private BigDecimal jackpot;
	private Date date;
	private String day;
	private String code;
	private int gameId;
	private String gameName;
	public int statusId;
	public String status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BigDecimal getJackpot() {
		return jackpot;
	}
	public void setJackpot(BigDecimal jackpot) {
		this.jackpot = jackpot;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
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
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int i) {
		this.statusId = i;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
