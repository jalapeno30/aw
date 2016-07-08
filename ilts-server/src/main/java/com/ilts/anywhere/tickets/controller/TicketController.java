package com.ilts.anywhere.tickets.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ilts.anywhere.tickets.service.TicketService;

@Controller
public class TicketController {
	
	@Autowired
	private TicketService ticketService;

	@RequestMapping("/tickets/listAll")
	public @ResponseBody ResponseEntity<Object> listAllTickets(
			HttpServletResponse response) {
		return ticketService.getAllTickets();
	}
	
}
