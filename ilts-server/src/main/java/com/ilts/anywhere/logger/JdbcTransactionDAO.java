package com.ilts.anywhere.logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JdbcTransactionDAO implements TransactionDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	JdbcTransactionDAO() {
	}

	@Override
	public void insert(String userId, String description, Object data) throws DataAccessException, JsonProcessingException {
		
		String sql = "INSERT INTO transaction_log(userId, description, data) VALUES(?, ?, ?)";
		
		this.jdbcTemplate.update(sql, new Object[]{
			userId, description, this.pojoToJson(data)
		});
		
	}
	
	private String pojoToJson(Object data) throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(data);
		
		return json;
	}
	
	
	
}
