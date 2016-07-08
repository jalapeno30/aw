package com.ilts.anywhere.betting.service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilts.anywhere.authentication.AuthenticationService;
import com.ilts.anywhere.betting.*;
import com.ilts.anywhere.betting.dao.BetDao;
import com.ilts.anywhere.betting.model.Bet;
import com.ilts.anywhere.coreplatform.service.CorePlatformService;
import com.ilts.anywhere.logger.TransactionLogger;
import com.ilts.anywhere.payment.BetPurchase;
import com.ilts.anywhere.payment.PaymentService;
import com.ilts.anywhere.response.Response;
import com.ilts.anywhere.response.ResponseFactory;
import com.ilts.anywhere.response.http.HttpResponse;
import com.ilts.anywhere.response.http.HttpResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.List;

/*
 * 
 */
@Service
@ComponentScan
public class BettingService {

	@Autowired	
	private CorePlatformService cpService;
	
	@Autowired
	private AuthenticationService authService;
	
	@Autowired
	private TransactionLogger tl;
	
	@Autowired
	private PaymentService ps;
	
	@Autowired
	private BetDao betDao;
	
	/*
	 * 
	 * @param purchaseJSON 	JSON string from frontend containing bet information
	 * @param userId		integer identifier of current user
	 */
//	public Response purchaseBet(String purchaseJSON, String userId) {
//		
//		// prepare bet information from JSON
//		OBet bet = BetFactory.makeBet(BetType.PCSO);
//		Purchase purchase = null;
//
//		try {
//
//			ObjectMapper mapper = new ObjectMapper();
//			
//			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//			purchase = mapper.readValue(purchaseJSON, Purchase.class);
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
//		// generate unique hash for bet
//		bet.initialize(purchase.getDate());
//
//		// call CPService to process the bet action
//		Response cpResponse = cpService.processBetAction(purchase.getOrders(), purchase.getDate(), userId);
//		
//		// set hooks to save
//		if (cpResponse.getStatus().equals("success")) {
//			cpService.lottoGameSell(purchase, cpResponse);
//		}
//		
//		return cpResponse;
//
//	}
	
	public ResponseEntity<Object> purchaseBet(String purchaseJSON, String token) {
		
		// check session validity
		Boolean validSession = authService.validSession(token);
		
		if (!validSession) {
			
			// log invalid session
			tl.log("Invalid Session");
			// return invalid session response
			HttpResponse response = ResponseFactory.makeHttpResponse(HttpResponseType.UNAUTHORIZED);
			return response.getResponseEntity();
			
		} else {
			
			// parse purchase JSON
			Purchase purchase = new Purchase();
			ObjectMapper mapper = new ObjectMapper();
			
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			try {
				purchase = mapper.readValue(purchaseJSON, Purchase.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// process payment
			Boolean hasFunding = ps.hasFunding(purchase.getOrders(), purchase.getDate(), purchase.getUserId());
			
			if (!hasFunding) {
				
				tl.log("Insufficient Funding");
				HttpResponse response = ResponseFactory.makeHttpResponse(HttpResponseType.BADREQUEST);
				return response.getResponseEntity();
				
			} else {
				
				tl.log("Start bet");
				HttpResponse response = ResponseFactory.makeHttpResponse(HttpResponseType.ACCEPTED);
				response.setData(new Object(){
					public String redirectURI = ps.getRedirectUri();
				});

				return response.getResponseEntity();
			}
		}

	}
	
	public Response cancelBet()
	{
		// call CPService to cancel bet
		
		return cpService.cancelBet(null, null);
	}
	
	public Double calculateBetCost()
	{
		
		return cpService.calculateBetCost();
	}
	
	public Response prevalidateBet()
	{
		return null;
	}
	
	public Response requestBetPayout()
	{
		return cpService.requestBetPayout();
	}
	
	/*
	 * 
	 */
	public List<BetPurchase> getPurchases() {
		
		return cpService.getPurchases();
	}
	
	/*
	 * 
	 */
	public List<PurchaseWrapper> getPurchases(String userId) {
		
		return cpService.getPurchases(userId);
	}
	
	/*
	 * 
	 */
	public List<BetPurchase> getPurchasesCustom(Boolean amountChecked,
			String amountCondition, Double amountValuesFirst,
			Double amountValuesSecond, Boolean dateChecked,
			String dateValuesEnd, String dateValuesStart,
			Boolean statusChecked, String[] statusValues, Boolean userChecked,
			Integer[] userValues) throws ParseException {
		
		return cpService.getPurchases(amountChecked,
			amountCondition, amountValuesFirst,
			amountValuesSecond, dateChecked,
			dateValuesEnd, dateValuesStart,
			statusChecked, statusValues, userChecked,
			userValues);
	}

	public List<Object> getRecoveryData() {
		
		return cpService.getRecoveryData();
		
	}

	public List<Object> getRecoveryData(Boolean statusChecked, String status,
			Boolean userChecked, Integer[] userValues, Boolean dateChecked, String dateValuesEnd, String dateValuesStart) {
		return cpService.getRecoveryData(statusChecked, status, userChecked, userValues, dateChecked, dateValuesEnd, dateValuesStart);
	}

	@Transactional
	public ResponseEntity<Object> testList() {
		Object data = betDao.listByUser(new BigInteger("1"));
		
		
		HttpResponse httpResponse = ResponseFactory.makeHttpResponse(HttpResponseType.ACCEPTED, data);
		return httpResponse.getResponseEntity();
	}
	
	@Transactional
	public ResponseEntity<Object> listPendingByUser(String token) {
		
		Boolean validSession = authService.validSession(token);
		
		if (!validSession) {
			
			HttpResponse httpResponse = ResponseFactory.makeHttpResponse(HttpResponseType.UNAUTHORIZED);
			return httpResponse.getResponseEntity();
			
		} else {
			
			String userId = authService.getUserId(token);
			Object data = betDao.listPendingByUser(userId);
			
			HttpResponse httpResponse = ResponseFactory.makeHttpResponse(HttpResponseType.ACCEPTED, data);
			return httpResponse.getResponseEntity();
			
		}
		
	}
	
	@Transactional
	public ResponseEntity<Object> pendingBetsCost(String token) {
		Boolean validSession = authService.validSession(token);
		
		if (!validSession) {
			
			HttpResponse httpResponse = ResponseFactory.makeHttpResponse(HttpResponseType.UNAUTHORIZED);
			return httpResponse.getResponseEntity();
			
		} else {
			
			String userId = authService.getUserId(token);
			Double cost = 0.0;
			Double tax = 0.0;
			Double total = 0.0;
			List<Bet> pendingBets = betDao.listPendingByUser(userId);
			
			for(Bet bet: pendingBets) {
				cost += bet.getBetCost();
				tax += bet.getBetTax();
				total += bet.getBetCost() + bet.getBetTax();
			}
			
			final Double betCost = cost;
			final Double betTax = tax;
			final Double betTotal = total;
			
			Object data = new Object(){
				private Double cost = betCost;
				private Double tax = betTax;
				private Double total = betTotal;
				public Double getCost() {
					return cost;
				}
				public Double getTax() {
					return tax;
				}
				public Double getTotal() {
					return total;
				}
			};
			
			HttpResponse httpResponse = ResponseFactory.makeHttpResponse(HttpResponseType.ACCEPTED, data);
			return httpResponse.getResponseEntity();	
		}
	}
	
}
