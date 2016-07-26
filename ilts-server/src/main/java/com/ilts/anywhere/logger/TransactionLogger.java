package com.ilts.anywhere.logger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;

import java.io.IOException;

import com.fasterxml.jackson.core.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ComponentScan
public class TransactionLogger {

	@Autowired
	private JdbcTransactionDAO transactionDAO;
	
	public void log(String log) {
		System.out.println(log);
	}	
	
	public void logTransaction(String userId, String description, Object data) {
	System.out.println(" ****** logTransaction Transaction lof ********* ");	
            try {
			this.transactionDAO.insert(userId, description, data);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void logBet(Integer userId, Integer purchaseId, String status) {
//		this.recoveryDAO.insert(userId, purchaseId, status);
	}
	
}