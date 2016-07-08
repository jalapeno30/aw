package com.ilts.anywhere.tickets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ilts.anywhere.response.ResponseFactory;
import com.ilts.anywhere.response.http.HttpResponse;
import com.ilts.anywhere.response.http.HttpResponseType;
import com.ilts.anywhere.tickets.dao.TicketDao;

@Service
@ComponentScan
public class TicketService {
	
	@Autowired
	private TicketDao ticketDao;
	
	public ResponseEntity<Object> getAllTickets() {
		
		// retrieve data
		Object data = ticketDao.list();
		
		HttpResponse httpResponse = ResponseFactory.makeHttpResponse(HttpResponseType.ACCEPTED, data);
		return httpResponse.getResponseEntity();
	}
	
}
