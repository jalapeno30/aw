package com.ilts.anywhere.payment;

import java.util.ArrayList;

public class PaypalResponse {
	
	private String id;
	private String create_time;
	private String update_time;
	private String state;
	private String intent;
	private ArrayList<PaypalLink> links;
	private PaypalPayer payer;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIntent() {
		return intent;
	}
	public void setIntent(String intent) {
		this.intent = intent;
	}
	public ArrayList<PaypalLink> getLinks() {
		return links;
	}
	public void setLinks(ArrayList<PaypalLink> links) {
		this.links = links;
	}
	public PaypalPayer getPayer() {
		return payer;
	}
	public void setPayer(PaypalPayer payer) {
		this.payer = payer;
	}

}
