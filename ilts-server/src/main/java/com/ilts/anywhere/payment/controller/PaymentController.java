/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: PaymentController.java
 */
package com.ilts.anywhere.payment.controller;

import com.ilts.anywhere.authentication.AuthenticationService;
import com.ilts.anywhere.betting.PurchaseWrapper;
import com.ilts.anywhere.betting.service.BettingService;
import com.ilts.anywhere.datastore.AnywhereDB;
import com.ilts.anywhere.payment.PaymentService;
import com.ilts.anywhere.payment.model.DummyCardDetails;
import com.ilts.anywhere.response.Response;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ssanapureddy
 */
@RestController
public class PaymentController {

    public String hash;

    @Autowired
    public PaymentService paymentService;

    @Autowired
    private AuthenticationService authService;

    @Autowired
    public BettingService bettingService;

    @Autowired
    public AnywhereDB anywhereDB;

    @RequestMapping("/payment/purchaseBet")
    public @ResponseBody
    ResponseEntity<Object> purchase(
            @RequestParam(value = "token", required = true) String token,
            @RequestBody String jsonRequest,
            HttpServletResponse response) {
        System.out.println("****/payment/purchaseBet");
        return bettingService.purchases(jsonRequest, token);// send transaction Id too

//		if (!authService.validSession(token)) {
//			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
//		} else {
//			int userId = authService.getUserId(token);
//			
//			final Object bet = bettingService.purchaseBet(jsonRequest, userId);
//
//			return new ResponseEntity<Object>(bet, HttpStatus.OK);
//		}
    }

// skip this for now
// this is developed for reports
//        @RequestMapping("/payment/getPurchases")
//	public @ResponseBody ResponseEntity<List<PurchaseWrapper>> getPurchases(
//			@RequestParam(value="token", required = true) String token,
//			HttpServletResponse response) {
//		
//		if (!authService.validSession(token)) {
//			
//			return new ResponseEntity<List<PurchaseWrapper>>(HttpStatus.UNAUTHORIZED);
//			
//		} else {
//			
//			String userId = authService.getUserId(token);
//			List<PurchaseWrapper> purchases = bettingService.getPurchases(userId);
//			return new ResponseEntity<List<PurchaseWrapper>>(purchases, HttpStatus.OK);
//			
//		}
//		
//	}
    @RequestMapping("/payment/existingCardDetails")
    public @ResponseBody
    ResponseEntity<List<DummyCardDetails>> existingCarddetails(@RequestParam(value = "token", required = true) String token,
            @RequestHeader String userId,
            HttpServletResponse response) {
        System.out.println("HEADER ________ " + userId);
        return this.bettingService.existingCardDetails(token, userId);
    }

//        Json String userId,
    // String cardid, 
    //   String transId,
    //   String dateModified,
    @RequestMapping("/payment/continueExistingCard")
    public @ResponseBody
    Response continueExistingcard(@RequestParam(value = "token", required = true) String token,
            @RequestBody String jsonExistingcard,
            HttpServletResponse response) {
        return this.bettingService.continueExistingcard(token,jsonExistingcard);
    }

    @RequestMapping("/payment/newCardDetails")
    public @ResponseBody
    Response NewCarddetails(@RequestParam(value = "token", required = true) String token,
            @RequestBody String jsonCard,
            HttpServletResponse response) {
        System.out.println(token);
        System.out.println(jsonCard);

        Response cardstatus = this.bettingService.newCardDetails(token, jsonCard);
        return cardstatus;
    }

    @RequestMapping("/payment/status")
    public @ResponseBody
    Response statusUpdate(@RequestParam(value = "token", required = true) String token,
            @RequestBody String statusJson,
            HttpServletResponse response) {
        System.out.println(token);
        System.out.println(statusJson);
Response status = this.bettingService.statusUpdateTrans(token, statusJson);
        return status;
    }
//                @RequestMapping("/payment/confirmationID")
//	public @ResponseBody String confirmUpdate(@RequestParam(value="token", required = true) String token,String username,String cardid, String status,
//			HttpServletResponse response){
//            return "Transid";
//        }

    //can be done later
    @RequestMapping("/payment/updateCard")
    public @ResponseBody
    Boolean updatecard(@RequestParam(value = "token", required = true) String token, String username, String transId, String status,
            HttpServletResponse response) {
        return true;
    }

    @RequestMapping("/payment/removeCard")
    public @ResponseBody
    String removeCard(@RequestParam(value = "token", required = true) String token, String username, String cardid, String status,
            HttpServletResponse response) {
        return " ";
    }

}
