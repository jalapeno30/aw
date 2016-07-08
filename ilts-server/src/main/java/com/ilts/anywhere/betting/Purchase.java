package com.ilts.anywhere.betting;

import java.util.ArrayList;

public class Purchase {

	private String date;
//	// private Order orders;
//	private int total;
//	private String token;
	private String userId;
	private ArrayList<String> orders;

	public Purchase(String date, ArrayList<String> orders) {
		this.orders = orders;
		this.date = date;
	}

	public Purchase() {}

	public ArrayList<String> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<String> orders) {
		this.orders = orders;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

//	public String getDate() {
//		return this.date;
//	}
//
//	public double getPrice() {
//		// LottoSystem.out.println(this.orders);
//		// return this.orders.getPrice();
//		return (double) this.total;
//	}
//
//	public String getToken() {
//		return this.token;
//	}
//	
//	public void setToken(String token) {
//		this.token = token;
//	}
	
}