/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: CartService.java
 */
package com.ilts.anywhere.cart;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.core.*;
import com.ilts.anywhere.authentication.JdbcSessionDAO;
import com.ilts.anywhere.datastore.AnywhereDB;

import com.ilts.anywhere.response.Response;
import com.ilts.anywhere.response.ResponseFactory;
import com.ilts.anywhere.response.ResponseType;
import com.ilts.anywhere.logging.log.CustomLogger;
import com.ilts.anywhere.utils.Logger;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import org.hibernate.HibernateException;

@Service
public class CartService {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcOrderDAO orderDAO;

    @Autowired
    private AnywhereDB anywhereDB;

    @Autowired
    private JdbcSessionDAO sessionDAO;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    CartService() {
    }

  
    public Response insertOrder(String orderJSON) {

        Orders order = new Orders();
        Response newOrderResponse = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            order = mapper.readValue(orderJSON, Orders.class);
        System.out.println("________________________________JSON FOR Order__________________________________________");
        System.out.println(order.getUserId());
        System.out.println(order.getToken());
        System.out.println(order.getNumbers());
        System.out.println(order.getGameId() + "  ----  " + order.getUserId());
        System.out.println(order.getDrawId());
        System.out.println(order.getSystemName());
        System.out.println("__________________________________________________________________________");
      
            if (this.sessionDAO.validSession(order.getToken())) {
                // get user id
                order.setUserId(this.sessionDAO.getUserId(order.getToken()));

                // get cost of order
                int orderCost = this.calculateOrderCost(order.getGameId().getGameId(), order.getSystemName(), order.getNumbers());
                order.setCost(orderCost);

                this.orderDAO.insert(order);
//                System.out.println("______________________INSERT AFTER ____________________________________________________"+order.;
                System.out.println("______________________INSERT AFTER ____________________________________________________"+order.getSystemName());

                Logger.logMsg(CustomLogger.LogType.INFO, true, "Successfully placed order for  " + order.getGameId() + " and system :" + order.getSystemName());

                newOrderResponse = ResponseFactory.makeResponse(ResponseType.SUCCESSNEWORDER);
                newOrderResponse.setMessage("Order placed successfully");
                return newOrderResponse;
            } else {
                Logger.logMsg(CustomLogger.LogType.DEBUG, true, "Error placing Order for" + order.getGameId()+ " and system :" + order.getSystemName());

                newOrderResponse = ResponseFactory.makeResponse(ResponseType.ERRORINVALIDSESSION);
                newOrderResponse.setMessage("Invalid Session");

            }
        } catch (SQLException | HibernateException ex) {
            Logger.logException(CustomLogger.LogType.SEVERE, true, "SQLException/Hibernate Exception", ex);
            newOrderResponse = ResponseFactory.makeResponse(ResponseType.ERRORINVALIDSESSION);
            newOrderResponse.setMessage(String.format("Order cannot be placed due to - %s", ex.getMessage()));
        } catch (JsonGenerationException ex) {
             Logger.logException(CustomLogger.LogType.SEVERE, true, "JsonGenerationException ", ex);
            newOrderResponse = ResponseFactory.makeResponse(ResponseType.ERRORINVALIDSESSION);
            newOrderResponse.setMessage(String.format("Order cannot be placed due to - %s", ex.getMessage()));
        } catch (IOException ex) {
            Logger.logException(CustomLogger.LogType.SEVERE, true, "IOException ", ex);
            newOrderResponse = ResponseFactory.makeResponse(ResponseType.ERRORINVALIDSESSION);
            newOrderResponse.setMessage(String.format("Order cannot be placed due to - %s", ex.getMessage()));
        } catch (Exception ex) {
            Logger.logException(CustomLogger.LogType.SEVERE, true, "Exception", ex);
            newOrderResponse = ResponseFactory.makeResponse(ResponseType.ERRORINVALIDSESSION);
            newOrderResponse.setMessage(String.format("Order cannot be placed due to - %s", ex.getMessage()));
        }
        return newOrderResponse;
    }

 
    public List<Order> getOrders(String userId) {
        List<Order> orders = null;
        try {
            orders = this.orderDAO.getAll(userId);
           
        } catch (HibernateException ex) {
            Logger.logException(CustomLogger.LogType.SEVERE, true, "HibernateException", ex);

        } catch (Exception ex) {
            Logger.logException(CustomLogger.LogType.SEVERE, true, "Exception", ex);

        }
 
      return orders;  
    }


    public List<Order> getAllOrders() {
        List<Order> orders = null;
        try {
            orders = this.orderDAO.getAll();
        } catch (HibernateException ex) {
            Logger.logException(CustomLogger.LogType.SEVERE, true, "HibernateException", ex);

        } catch (Exception ex) {
            Logger.logException(CustomLogger.LogType.SEVERE, true, "Exception", ex);

        }
        return orders;
    }


    public void deleteOrder(String id) {
        try {
            this.orderDAO.delete(id);
        }catch (HibernateException ex) {
            Logger.logException(CustomLogger.LogType.SEVERE, true, "HibernateException", ex);

        } catch (Exception ex) {
            Logger.logException(CustomLogger.LogType.SEVERE, true, "Exception", ex);

        }
    }


    private int calculateOrderCost(String gameId, String system, ArrayList<ArrayList<Integer>> numbers) {
        // retrieve game cost
        int gameCost = this.anywhereDB.getCost(gameId);

        // retrieve system
        int systemInt = this.anywhereDB.getSystem(system);

        // retrieve sets of numbers
        int numberSets = numbers.size();

        int totalCost = (this.factorial(systemInt).divide(this.factorial(6).multiply(this.factorial(systemInt - 6)))).intValue() * gameCost * numberSets;

        return totalCost;
    }

    public static BigInteger factorial(int number) {
        BigInteger factValue = BigInteger.ONE;

        for (int i = 2; i <= number; i++) {
            factValue = factValue.multiply(BigInteger.valueOf(i));
        }

        return factValue;
    }

//	public Response registerUser(String registerJSON) {
//		
//		UserWrapper userWrap = new UserWrapper();
//		
//		try {
//
//			ObjectMapper mapper = new ObjectMapper();
//			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//			userWrap = mapper.readValue(registerJSON, UserWrapper.class);
//			
//
//		} catch (JsonGenerationException e) {
// 
//			e.printStackTrace();
//	 
//		} catch (IOException e) {
//	 
//			e.printStackTrace();
//	 
//		}
//		
//		User user = new User();
//		user.setUserName(userWrap.getUsername());
//		user.setPassword(userWrap.getPassword());
//		user.setFirstName(userWrap.getFirstName());
//		user.setLastName(userWrap.getLastName());
//		user.setBirthDate(userWrap.getBirthDate());
//		user.setGender(userWrap.getGender());
//		this.userDAO.register(user);
//        
//        Response registerResponse = ResponseFactory.makeResponse(ResponseType.SUCCESSREGISTER);
//        
//        return registerResponse;
//	}
//	
//	public Response loginUser(String username, String password) {
//		
//		User user = this.userDAO.login(username, password);
//		
//		if (user != null) {
//			Session session = new Session(user.getUserId());
//			this.sessionDAO.insert(session);
//			
//			LoginResponse loginResponse = (LoginResponse)ResponseFactory.makeResponse(ResponseType.SUCCESSLOGIN);
//			loginResponse.setSession(session);
//			
//			return loginResponse;
//			
//		} else {
//			Response loginResponse = ResponseFactory.makeResponse(ResponseType.ERROR);
//			loginResponse.setMessage("Error logging in");
//			
//			return loginResponse;
//		}
//	}
}
