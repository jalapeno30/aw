package com.ilts.anywhere.payment;

public class PaypalPayerInfo {
	private String email;
	private String first_name;
	private String last_name;
	private String payer_id;
	private PaypalPayerInfoShippingAddress shipping_address;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getPayer_id() {
		return payer_id;
	}
	public void setPayer_id(String payer_id) {
		this.payer_id = payer_id;
	}
	public PaypalPayerInfoShippingAddress getShipping_address() {
		return shipping_address;
	}
	public void setShipping_address(PaypalPayerInfoShippingAddress shipping_address) {
		this.shipping_address = shipping_address;
	}
}
