package com.ilts.anywhere.betting;

public abstract class OBet {

	public OBet(BetType betType) {
		this.betType = betType;
		processBet();
	}

	private void processBet() {

	}

	protected abstract void construct();

	private BetType betType = null;
	private String id = "";
	private String status = "";

	public BetType getBetType() {
		return betType;
	}

	public void setBetType(BetType betType) {
		this.betType = betType;
	}

	public void initialize() {}
	public void initialize(String date) {}

	public String getBetId() {
		return this.id;
	}

	protected void setBetId(String id) {
		this.id = id;
	}

	public void setOrders(Order[] orders) {}

	public void getStatus(String status) {
		this.status = status;
	}

	public String setStatus() {
		return this.status;
	}
}
