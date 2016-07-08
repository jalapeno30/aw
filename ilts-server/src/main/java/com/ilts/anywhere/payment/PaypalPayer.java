package com.ilts.anywhere.payment;

public class PaypalPayer {

	private String payment_method;
	private PaypalPayerInfo payer_info;
	public String getPayment_method() {
		return payment_method;
	}
	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}
	public PaypalPayerInfo getPayer_info() {
		return payer_info;
	}
	public void setPayer_info(PaypalPayerInfo payer_info) {
		this.payer_info = payer_info;
	}
}
