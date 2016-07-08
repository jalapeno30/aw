package com.ilts.anywhere.betting;

public class Order {

	private OrderDetail[] details;
	private int total;

	public Order(OrderDetail[] details, int totalAmount) {
		this.details = details;
		this.total = totalAmount;
	}

	// public Order() {}

	public double getPrice() {
		System.out.println(this.total);
		return (double) this.total;
	}

}