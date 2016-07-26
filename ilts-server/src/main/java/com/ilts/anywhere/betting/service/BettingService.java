package com.ilts.anywhere.betting.service;


/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: BettingService.java
 */
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
import com.ilts.anywhere.datastore.AnywhereDB;
import com.ilts.anywhere.logger.TransactionLogger;
import com.ilts.anywhere.logging.log.CustomLogger;
import com.ilts.anywhere.payment.BetPurchase;
import com.ilts.anywhere.payment.PaymentService;
import com.ilts.anywhere.payment.model.DummyCardDetails;
import com.ilts.anywhere.payment.model.ExistingCardJSON;
import com.ilts.anywhere.payment.model.PaymentPurchaseOrders;
import com.ilts.anywhere.payment.model.PaymentPurchases;
import com.ilts.anywhere.payment.model.StatusJson;
import com.ilts.anywhere.response.Response;
import com.ilts.anywhere.response.ResponseFactory;
import com.ilts.anywhere.response.ResponseType;
import com.ilts.anywhere.response.http.HttpResponse;
import com.ilts.anywhere.response.http.HttpResponseType;
import com.ilts.anywhere.utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.http.HttpStatus;

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
    AnywhereDB anywhereDb;

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
//                        System.out.println("----------------------- purchase Bet (String purchaseJSON, String userId)--------------------");
//                        System.out.println(">>>>>>>"+purchase);
//                        System.out.println(">>>>>>>"+purchase.getOrders());
//                        for(int i =0;i<purchase.getOrders().size();i++)
//                        System.out.println("************ >>>>"+purchase.getOrders().get(i));
//                        System.out.println(">>>>>>>"+purchase.getUserId());
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
//		System.out.println(" >>>> after cpService.processBetAction");
//		// set hooks to save
//		if (cpResponse.getStatus().equals("success")) {
//			cpService.lottoGameSell(purchase, cpResponse);
//                        System.out.println(" >>>> after cpService.lottoGameSell");
//
//		}
//		
//		return cpResponse;
//
//	}
    public ResponseEntity<Object> purchaseBet(String purchaseJSON, String token) {
        System.out.println("----------------------- purchase Bet (String purchaseJSON, String token)--------------------");
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
                response.setData(new Object() {
                    public String redirectURI = ps.getRedirectUri();

                });
                System.out.println(">>>>>>> AFTER  redirect URI Betting service" + response.getData());

                return response.getResponseEntity();
            }
        }

    }

    public Response cancelBet() {
        // call CPService to cancel bet

        return cpService.cancelBet(null, null);
    }

    public Double calculateBetCost() {

        return cpService.calculateBetCost();
    }

    public Response prevalidateBet() {
        return null;
    }

    public Response requestBetPayout() {
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
        System.out.println(">>>>>>>>>>>> List<PurchaseWrapper> getPurchases(String userId) Betting service**" + userId);

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

            for (Bet bet : pendingBets) {
                cost += bet.getBetCost();
                tax += bet.getBetTax();
                total += bet.getBetCost() + bet.getBetTax();
            }

            final Double betCost = cost;
            final Double betTax = tax;
            final Double betTotal = total;

            Object data = new Object() {
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
    //INSERTS TRANSACTION FOR ATHE ORDERS
    // get orders object and user is from front end

    @Transactional
    public ResponseEntity<Object> purchases(String purchaseJSON, String token) {
        PaymentPurchases paymentPurchases = null;
        PaymentPurchaseOrders paymentPurchaseOrders = null;
        HttpResponse response = null;

        Boolean validSession = authService.validSession(token);
        if (!validSession) {

            // log invalid session
            tl.log("Invalid Session");
            // return invalid session response
            response = ResponseFactory.makeHttpResponse(HttpResponseType.UNAUTHORIZED);
            return response.getResponseEntity();

        } else {
            try {
                ObjectMapper mapperPurchase = new ObjectMapper();
                mapperPurchase.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                paymentPurchases = mapperPurchase.readValue(purchaseJSON, PaymentPurchases.class);
                ObjectMapper mapperPurchaseOrder = new ObjectMapper();
                mapperPurchaseOrder.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                paymentPurchaseOrders = mapperPurchaseOrder.readValue(purchaseJSON, PaymentPurchaseOrders.class);
                System.out.println();
                System.out.println("****************************JSON parse**************************************");
                System.out.println("paymentPurchases.(): " + paymentPurchases.getUserId());

                System.out.println("paymentPurchases Date.(): " + paymentPurchases.getDate());

                for (int i = 0; i < paymentPurchaseOrders.getOrder().size(); i++) {
                    System.out.println(">>>>" + paymentPurchaseOrders.getOrder().get(i));
                }
                final String transactionId = this.anywhereDb.createPaymentPurchase((ArrayList<String>) paymentPurchaseOrders.getOrder(), paymentPurchases.getUserId(), paymentPurchases.getDate());
                response = ResponseFactory.makeHttpResponse(HttpResponseType.ACCEPTED);
                response.setData(new Object() {
                    public String transId = transactionId;

                });
            } catch (Exception e) {
                Logger.logException(CustomLogger.LogType.SEVERE, Boolean.TRUE, "JSON parse failed", e);
            }
            return response.getResponseEntity();
        }
    }

    @Transactional
    public ResponseEntity<List<DummyCardDetails>> existingCardDetails(String token, String userId) {
        HttpResponse response = null;
        Boolean validSession = authService.validSession(token);
        if (!validSession) {

            // log invalid session
            tl.log("Invalid Session");
            // return invalid session response
            response = ResponseFactory.makeHttpResponse(HttpResponseType.UNAUTHORIZED);
           return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        } else {
            response = ResponseFactory.makeHttpResponse(HttpResponseType.ACCEPTED);
            List<DummyCardDetails> cardDetails = this.anywhereDb.getExistingcarddetails(userId);
            return new ResponseEntity<List<DummyCardDetails>>(cardDetails, HttpStatus.OK);
        }

        //To change body of generated methods, choose Tools | Templates.
    }

    @Transactional
    public Response newCardDetails(String token, String jsonCard) {
        DummyCardDetails dummyCardDetails = null;

        Response cardResponse = null;
        try {
            ObjectMapper mapperCard = new ObjectMapper();
            mapperCard.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            dummyCardDetails = mapperCard.readValue(jsonCard, DummyCardDetails.class);
            System.out.println("******* CARD JSON *********");
            System.out.println("******** " + dummyCardDetails.getCardEmailId());
            System.out.println("******** " + dummyCardDetails.getCardNumber());
            System.out.println("******** " + dummyCardDetails.getTransId());
            cardResponse = ResponseFactory.makeResponse(ResponseType.SUCCESSDATA);

        } catch (Exception e) {
            Logger.logException(CustomLogger.LogType.SEVERE, Boolean.TRUE, "JSON parse failed", e);
        }
        return cardResponse;
    }

    @Transactional
    public Response continueExistingcard(String jsonExistingCard, String token) {
        Response response = null;
        ExistingCardJSON existingCardJSON = null;
        try {
            ObjectMapper mapperExist = new ObjectMapper();
            mapperExist.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            existingCardJSON = mapperExist.readValue(jsonExistingCard, ExistingCardJSON.class);
            System.out.println("*******Existing CARD JSON *********");
            System.out.println("******** " + existingCardJSON.getTransId());
            response = ResponseFactory.makeResponse(ResponseType.SUCCESSDATA);

        } catch (Exception e) {
            Logger.logException(CustomLogger.LogType.SEVERE, Boolean.TRUE, "JSON parse failed", e);
        }
        return response;

    }
    
    
    @Transactional
    public Response statusUpdateTrans(String token, String statusJson){
     Response response = null;
        StatusJson statusJSON = null;
        try {
            ObjectMapper mapperExist = new ObjectMapper();
            mapperExist.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            statusJSON = mapperExist.readValue(statusJson, StatusJson.class);
            System.out.println("*******STATUS JSON *********");
            System.out.println("******** " + statusJSON.getTransId());
            this.anywhereDb.updateStatus(statusJSON);
            response = ResponseFactory.makeResponse(ResponseType.SUCCESSDATA);
            response.setMessage("updated");

        } catch (IOException | ParseException e) {
            Logger.logException(CustomLogger.LogType.SEVERE, Boolean.TRUE, "JSON parse failed", e);
        }
      
      
      return response;
    }
}
