///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.ilts.anywhere.payment.service;
//
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ilts.anywhere.authentication.AuthenticationService;
//import com.ilts.anywhere.betting.Purchase;
//import com.ilts.anywhere.betting.dao.BetDao;
//import com.ilts.anywhere.coreplatform.service.CorePlatformService;
//import com.ilts.anywhere.logger.TransactionLogger;
//import com.ilts.anywhere.response.ResponseFactory;
//import com.ilts.anywhere.response.http.HttpResponse;
//import com.ilts.anywhere.response.http.HttpResponseType;
//import java.io.IOException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
///**
// *
// * @author ssanapureddy
// */
////@Service
////@ComponentScan
//public class PaymentService {
//    @Autowired	
//	private CorePlatformService cpService;
//	
//	@Autowired
//	private AuthenticationService authService;
//	
//	@Autowired
//	private TransactionLogger tl;
//	
//	@Autowired
//	private com.ilts.anywhere.payment.PaymentService ps;
//	
//	@Autowired
//	private BetDao betDao;
//        	public ResponseEntity<Object> purchaseBet(String purchaseJSON, String token) {
//		
//		// check session validity
//		Boolean validSession = authService.validSession(token);
//		
//		if (!validSession) {
//			
//			// log invalid session
//			tl.log("Invalid Session");
//			// return invalid session response
//			HttpResponse response = ResponseFactory.makeHttpResponse(HttpResponseType.UNAUTHORIZED);
//			return response.getResponseEntity();
//			
//		} else {
//			
//			// parse purchase JSON
//			Purchase purchase = new Purchase();
//			ObjectMapper mapper = new ObjectMapper();
//			
//			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//			try {
//				purchase = mapper.readValue(purchaseJSON, Purchase.class);
//			} catch (JsonParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (JsonMappingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			// process payment
//			Boolean hasFunding = ps.hasFunding(purchase.getOrders(), purchase.getDate(), purchase.getUserId());
//			
//			if (!hasFunding) {
//				
//				tl.log("Insufficient Funding");
//				HttpResponse response = ResponseFactory.makeHttpResponse(HttpResponseType.BADREQUEST);
//				return response.getResponseEntity();
//				
//			} else {
//				
//				tl.log("Start bet");
//				HttpResponse response = ResponseFactory.makeHttpResponse(HttpResponseType.ACCEPTED);
//				response.setData(new Object(){
//					public String redirectURI = ps.getRedirectUri();
//				});
//
//				return response.getResponseEntity();
//			}
//		}
//
//	}
//}
