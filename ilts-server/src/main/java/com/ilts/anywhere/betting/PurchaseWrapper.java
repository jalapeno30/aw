package com.ilts.anywhere.betting;

public class PurchaseWrapper {
	
	private int id;
	private String date;
	private String system;
	private String gameName;
	private String drawDate;
	private int cost;
	private String paypalId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getDrawDate() {
		return drawDate;
	}
	public void setDrawDate(String drawDate) {
		this.drawDate = drawDate;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public String getPaypalId() {
		return paypalId;
	}
	public void setPaypalId(String paypalId) {
		this.paypalId = paypalId;
	}
	
}
