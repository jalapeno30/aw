package com.ilts.anywhere.logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class JdbcRecoveryDAO implements RecoveryDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	JdbcRecoveryDAO() {
	}

	@Override
	public void insert(Integer userId, Integer transactionId, String status) {
		
		String sql = "INSERT INTO recovery_table(userId, transactionId, status) VALUES(?, ?, ?)";
		
		this.jdbcTemplate.update(sql, new Object[]{
			userId, transactionId, status
		});
		
	}
	
}
