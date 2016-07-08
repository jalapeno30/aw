package com.ilts.anywhere.coreplatform.lotto;

import java.sql.Date;
import java.util.List;

public class LottoGameSellResponse {
	private Date timestamp;
	private Integer numberOfDraws;
	private List<String> drawList;
	private String tsn;
	private Double ticketCost;
	private Double ticketTax;
	
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public Integer getNumberOfDraws() {
		return numberOfDraws;
	}
	public void setNumberOfDraws(Integer numberOfDraws) {
		this.numberOfDraws = numberOfDraws;
	}
	public List<String> getDrawList() {
		return drawList;
	}
	public void setDrawList(List<String> drawList) {
		this.drawList = drawList;
	}
	public String getTsn() {
		return tsn;
	}
	public void setTsn(String tsn) {
		this.tsn = tsn;
	}
	public Double getTicketCost() {
		return ticketCost;
	}
	public void setTicketCost(Double ticketCost) {
		this.ticketCost = ticketCost;
	}
	public Double getTicketTax() {
		return ticketTax;
	}
	public void setTicketTax(Double ticketTax) {
		this.ticketTax = ticketTax;
	}
}