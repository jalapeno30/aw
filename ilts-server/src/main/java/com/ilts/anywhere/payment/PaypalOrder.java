package com.ilts.anywhere.payment;

public class PaypalOrder {

	private Integer orderCost;
	private String drawDate;
	private String gameName;
	private Integer gameCost;
	private Integer quantity;
	public Integer getOrderCost() {
		return orderCost;
	}
	public void setOrderCost(Integer orderCost) {
		this.orderCost = orderCost;
	}
	public String getDrawDate() {
		return drawDate;
	}
	public void setDrawDate(String drawDate) {
		this.drawDate = drawDate;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public Integer getGameCost() {
		return gameCost;
	}
	public void setGameCost(Integer gameCost) {
		this.gameCost = gameCost;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public void calculateQuantity() {
		this.quantity = this.orderCost / this.gameCost;
	}
}
