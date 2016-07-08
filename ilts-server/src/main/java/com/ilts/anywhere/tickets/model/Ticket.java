package com.ilts.anywhere.tickets.model;

import java.math.BigInteger;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tickets")
public class Ticket {
	
	@Id
	@Column(name = "ticketId")
	private BigInteger ticketId;
	private String ticketSerialNumber;
	private Date ticketTimeStamp;
	private Double ticketCost;
	private Double ticketTax;
	public BigInteger getTicketId() {
		return ticketId;
	}
	public void setTicketId(BigInteger ticketId) {
		this.ticketId = ticketId;
	}
	public String getTicketSerialNumber() {
		return ticketSerialNumber;
	}
	public void setTicketSerialNumber(String ticketSerialNumber) {
		this.ticketSerialNumber = ticketSerialNumber;
	}
	public Date getTicketTimeStamp() {
		return ticketTimeStamp;
	}
	public void setTicketTimeStamp(Date ticketTimeStamp) {
		this.ticketTimeStamp = ticketTimeStamp;
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
