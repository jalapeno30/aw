package com.ilts.anywhere.tickets.dao;

import java.math.BigInteger;
import java.util.List;

import com.ilts.anywhere.tickets.model.Ticket;

public interface TicketDao {
	
	public void save(Ticket ticket);
	public Ticket fetchById(BigInteger ticketId);
	public List<Ticket> list();
}
