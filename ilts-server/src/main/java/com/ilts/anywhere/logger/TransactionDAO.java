package com.ilts.anywhere.logger;

import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface TransactionDAO {
	
	public void insert(String userId, String description, Object data) throws DataAccessException, JsonProcessingException;
	
}
