package com.ilts.anywhere.logger;

public interface RecoveryDAO {

	public void insert(Integer userId, Integer transactionId, String status);
	
}
