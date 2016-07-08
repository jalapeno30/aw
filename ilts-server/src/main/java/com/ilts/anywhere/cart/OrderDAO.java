package com.ilts.anywhere.cart;

import java.sql.SQLException;
import java.util.List;
import org.hibernate.HibernateException;

public interface OrderDAO {

	public void insert(Orders order) throws SQLException,Exception;
	
	public List<Order> getAll() throws SQLException,HibernateException,Exception;
	
	public List<Order> getAll(String userId) throws SQLException,HibernateException,Exception;
	
	public void get() throws SQLException,HibernateException,Exception;
	
	public void delete(String id) throws SQLException,HibernateException,Exception;
}
