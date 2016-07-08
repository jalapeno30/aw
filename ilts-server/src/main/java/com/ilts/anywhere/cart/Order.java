package com.ilts.anywhere.cart;

public abstract class Order {

	public Order(OrderType orderType) {
		this.orderType = orderType;
		processOrder();
	}

	private void processOrder() {

	}

	protected abstract void construct();
	
	private OrderType orderType = null;
	
}
