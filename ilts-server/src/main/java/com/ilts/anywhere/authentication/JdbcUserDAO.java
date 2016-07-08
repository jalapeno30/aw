package com.ilts.anywhere.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JdbcUserDAO {
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	JdbcUserDAO() {
	}
	
	public void register(User user) {
		this.register(user, "User", "Active");
	}
	
	public void register(User user, String role, String status) {
		
		final User newUser = user;
    	
    	final String userSQL = "INSERT INTO users(username, password, "
    			+ "firstName, lastName, birthDate, gender) VALUES(?, ?, ?, ?, ?, ?)";
    	
    	KeyHolder keyHolder = new GeneratedKeyHolder();
    	int row;
    	
    	// insert to users table
    	row = this.jdbcTemplate.update(new PreparedStatementCreator(){
    		public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
    			PreparedStatement ps = connection.prepareStatement(userSQL, Statement.RETURN_GENERATED_KEYS);
    			ps.setString(1, newUser.getUserName());
    			ps.setString(2, newUser.getPassword());
    			ps.setString(3, newUser.getFirstName());
    			ps.setString(4, newUser.getLastName());
    			ps.setString(5, newUser.getBirthDate());
    			ps.setString(6, newUser.getGender());
    			return ps;
    		}
    	}, keyHolder);
    	
    	// retrieve role_id
    	final String roleSearchSQL = "SELECT id FROM roles WHERE name = ?";
    	Integer roleId = this.jdbcTemplate.queryForObject(roleSearchSQL, new Object[]{role}, Integer.class);
    	
		// insert to roles table
    	final String rolesSQL = "INSERT INTO user_roles(user_id, role_id) VALUES(?, ?)";
    	this.jdbcTemplate.update(rolesSQL, new Object[]{keyHolder.getKey(), roleId});
    	
    	// retrieve status_id
    	final String statusSearchSQL = "SELECT id FROM user_statuses WHERE name = ?";
    	Integer statusId = this.jdbcTemplate.queryForObject(statusSearchSQL, new Object[]{status}, Integer.class);
    	
    	// insert to status table
    	final String statusSQL = "INSERT INTO user_status(user_id, status_id) VALUES(?, ?)";
    	this.jdbcTemplate.update(statusSQL, new Object[]{keyHolder.getKey(), statusId});
    	
	}

	public User getUserById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean validUser(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	public User login(String username, String password) {
		// TODO Auto-generated method stub
		
		final String sql = "SELECT users.id AS user_id, users.username AS username, users.password AS password, roles.name as role FROM users "
				+ "JOIN user_roles ON users.id = user_roles.user_id "
				+ "JOIN roles ON user_roles.role_id = roles.id "
				+ "WHERE username=? and password=?";
		
		try {

			User user = (User) this.jdbcTemplate.queryForObject(sql, new Object[] {username, password},
			new RowMapper() {
				
				public User mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					User user = new User();
					user.setUserName(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setUserID(rs.getString("user_id"));
					user.setRole(rs.getString("role"));
					return user;
				}
				
			});
			
			return user;
	        
		} catch (Exception e) {
 
			e.printStackTrace();
			throw new RuntimeException(e);
	 
		}
		
//		return null;
	}
	
	public List<User> getAllUsers() {
		
		final String sql = "SELECT users.id AS user_id, users.username AS username, roles.name AS role, user_statuses.name AS status "
				+ "FROM users "
				+ "JOIN user_roles ON users.id = user_roles.user_id "
				+ "JOIN roles ON user_roles.role_id = roles.id "
				+ "JOIN user_status ON users.id = user_status.user_id "
				+ "JOIN user_statuses ON user_status.status_id = user_statuses.id";
		
		List<User> users = new ArrayList<User>();
		
		List<Map<String,Object>> rows = this.jdbcTemplate.queryForList(sql);
		for (Map row: rows) {
			User user = new User();
			user.setUserID(((BigInteger)(row.get("user_id"))).toString());
			user.setUserName((String)(row.get("username")));
			user.setRole((String)(row.get("role")));
			user.setStatus((String)(row.get("status")));
			users.add(user);
		}
		
		return users;
	}
	
	public List<Object> getStatuses() {
		
		final String sql = "SELECT * FROM user_statuses";
		
		List<Object> statuses = new ArrayList<Object>();
		
		List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(sql);
		for (Map row: rows) {
			final Map hrow = row;
			Object status = new Object(){
				public String id = ((Integer)((Long)(hrow.get("id"))).intValue()).toString();
				public String name = (String)(hrow.get("name"));
			};
			statuses.add(status);
		}
		return statuses;
	}
	
	public List<Role> getRoles() {
		
		final String sql = "SELECT * FROM roles";
		
		List<Role> roles = new ArrayList<Role>();
		
		List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(sql);
		for (Map row: rows) {
			final Map hrow = row;
			Role role = new Role();
			role.setId(((Long)(hrow.get("id"))).intValue());
			role.setName((String)(hrow.get("name")));
			roles.add(role);
		}
		return roles;
	}
	
	public void changeStatus(String userId, String statusId) {
		
		final String sql = "UPDATE user_status SET status_id = ? WHERE user_id = ?";
		
		try {
			
			this.jdbcTemplate.update(sql, new Object[]{statusId, userId});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void changeRole(String userId, String roleId) {
		
		final String sql = "UPDATE user_roles SET role_id = ? WHERE user_id = ?";
		
		try {
			
			this.jdbcTemplate.update(sql, new Object[]{roleId, userId});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
