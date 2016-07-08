package com.ilts.anywhere.cart;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;


public class PCSOOrder extends Order {
	
	PCSOOrder() {
		super(OrderType.PCSO);
		construct();
	}

	@Override
	protected void construct() {
		// TODO Auto-generated method stub

	}
	
	private int id;
	private String gameName;
	private BigDecimal gameCost;
	private int orderCost;
	private BigDecimal drawJackpot;
	private String drawDate;
	private String drawDay;
	private String drawCode;
	private ArrayList<List<String>> numbers;
	private String system;
	private String username;
	private Boolean deleted;
	private Boolean active;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public BigDecimal getGameCost() {
		return gameCost;
	}

	public void setGameCost(BigDecimal gameCost) {
		this.gameCost = gameCost;
	}

	public BigDecimal getDrawJackpot() {
		return drawJackpot;
	}

	public void setDrawJackpot(BigDecimal drawJackpot) {
		this.drawJackpot = drawJackpot;
	}

	public String getDrawDate() {
		return drawDate;
	}

	public void setDrawDate(String drawDate) {
		this.drawDate = drawDate;
	}

	public String getDrawDay() {
		return drawDay;
	}

	public void setDrawDay(String drawDay) {
		this.drawDay = drawDay;
	}

	public String getDrawCode() {
		return drawCode;
	}

	public void setDrawCode(String drawCode) {
		this.drawCode = drawCode;
	}

	public ArrayList<List<String>> getNumbers() {
		return numbers;
	}

	public void setNumbers(ArrayList<List<String>> numbers) {
		this.numbers = numbers;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public int getOrderCost() {
		return orderCost;
	}

	public void setOrderCost(int orderCost) {
		this.orderCost = orderCost;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
}
