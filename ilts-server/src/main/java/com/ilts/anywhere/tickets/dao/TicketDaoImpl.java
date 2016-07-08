package com.ilts.anywhere.tickets.dao;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ilts.anywhere.tickets.model.Ticket;

public class TicketDaoImpl implements TicketDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public TicketDaoImpl() {
		
	}
	
//	public TicketDaoImpl(SessionFactory sessionFactory) {
//		this.sessionFactory = sessionFactory;
//	}
	
//	public void setSessionFactory(SessionFactory sessionFactory) {
//		this.sessionFactory = sessionFactory;
//	}

	@Override
	public void save(Ticket ticket) {
		// TODO Auto-generated method stub

	}

	@Override
	public Ticket fetchById(BigInteger ticketId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public List<Ticket> list() {
		
		List<Ticket> listTicket = (List<Ticket>) sessionFactory.getCurrentSession()
				.createCriteria(Ticket.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
		
		return listTicket;
	}

}
