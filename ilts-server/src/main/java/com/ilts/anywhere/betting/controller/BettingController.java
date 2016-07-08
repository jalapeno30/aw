package com.ilts.anywhere.betting.controller;

import com.ilts.anywhere.authentication.AuthenticationService;
import com.ilts.anywhere.betting.PurchaseWrapper;
import com.ilts.anywhere.betting.service.BettingService;
import com.ilts.anywhere.datastore.AnywhereDB;
import com.ilts.anywhere.payment.BetPurchase;
import com.ilts.anywhere.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

// import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BettingController {

	public String hash;

	@Autowired
	public PaymentService paymentService;

	@Autowired
	private AuthenticationService authService;
	
	@Autowired
	public BettingService bettingService;

	@Autowired
	public AnywhereDB anywhereDB;

	/**
	* Used to place bets for various games. Confirmation ID will be used to cancel bets through another service. 
	* On success will save ticket information in User DB, which will be used to retrieve winnings.
	**/
	@RequestMapping("/betting/purchaseBet")
	public @ResponseBody ResponseEntity<Object> purchase(
			@RequestParam(value="token", required = true) String token,
			@RequestBody String jsonRequest,
			HttpServletResponse response) {
		
		return bettingService.purchaseBet(jsonRequest, token);
		
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

	/**
	* Cancel a valid (still able to be canceled) bet. Assumption here is that they will have a 
	* certain period of time to cancel a bet.
	**/
	@RequestMapping("/betting/cancelBet")
	public @ResponseBody Object cancel(
			HttpServletResponse response) {

		return new Object() {};
	}

	/**
	* To determine the initial bet cost
	**/
	@RequestMapping("/betting/calculateBetCost")
	public @ResponseBody Object calculate(
			HttpServletResponse response) {

		return new Object() {};
	}

	/**
	* to pre-validate the bet request to make sure that the game is still allowing bets to be placed.
	**/
	@RequestMapping("/betting/prevalidateBet")
	public @ResponseBody Object prevalidate(
			HttpServletResponse response) {

		return new Object() {};
	}

	/**
	* To automatically pay the bet payout. Some clients may require that the user explicitly 
	* request the bet payout from the UI.
	**/
	@RequestMapping("/betting/requestBetPayout")
	public @ResponseBody Object payout(
			HttpServletResponse response) {

		return new Object() {};
	}
	
	@RequestMapping("/betting/getPurchases")
	public @ResponseBody ResponseEntity<List<PurchaseWrapper>> getPurchases(
			@RequestParam(value="token", required = true) String token,
			HttpServletResponse response) {
		
		if (!authService.validSession(token)) {
			
			return new ResponseEntity<List<PurchaseWrapper>>(HttpStatus.UNAUTHORIZED);
			
		} else {
			
			String userId = authService.getUserId(token);
			List<PurchaseWrapper> purchases = bettingService.getPurchases(userId);
			return new ResponseEntity<List<PurchaseWrapper>>(purchases, HttpStatus.OK);
			
		}
		
	}
	
	@RequestMapping("/betting/getPurchasesCustom")
	public @ResponseBody ResponseEntity<List<BetPurchase>> getPurchasesCustom(
			@RequestParam(value="token", required = true) String token,
			@RequestParam(value="amount_checked", required = false, defaultValue="false") Boolean amountChecked,
			@RequestParam(value="amount_condition", required = false, defaultValue="is") String amountCondition,
			@RequestParam(value="amount_values_first", required = false, defaultValue="0") Double amountValuesFirst,
			@RequestParam(value="amount_values_second", required = false, defaultValue="0") Double amountValuesSecond,
			@RequestParam(value="date_checked", required = false, defaultValue="false") Boolean dateChecked,
			@RequestParam(value="date_values_end", required = false, defaultValue="") String dateValuesEnd,
			@RequestParam(value="date_values_start", required = false, defaultValue="") String dateValuesStart,
			@RequestParam(value="status_checked", required = false, defaultValue="false") Boolean statusChecked,
			@RequestParam(value="status_values[]", required=false, defaultValue="") String[] statusValues,
			@RequestParam(value="user_checked", required = false, defaultValue="false") Boolean userChecked,
			@RequestParam(value="user_values[]", required = false, defaultValue="0") Integer[] userValues,
			HttpServletResponse response) throws ParseException {
		
		if (!authService.validSession(token)) {
			
			return new ResponseEntity<List<BetPurchase>>(HttpStatus.UNAUTHORIZED);
			
		} else {
			
			String userId = authService.getUserId(token);
			List<BetPurchase> purchases = bettingService.getPurchasesCustom(
						amountChecked,
						amountCondition,
						amountValuesFirst,
						amountValuesSecond,
						dateChecked,
						dateValuesEnd,
						dateValuesStart,
						statusChecked,
						statusValues,
						userChecked,
						userValues
					);
			return new ResponseEntity<List<BetPurchase>>(purchases, HttpStatus.OK);
			
		}
		
	}
	
	@RequestMapping("/betting/getAllPurchases")
	public @ResponseBody ResponseEntity<List<BetPurchase>> getAllPurchases(
			@RequestParam(value="token", required = true) String token,
			HttpServletResponse response) {
		
		if (!authService.validSession(token)) {
			
			return new ResponseEntity<List<BetPurchase>>(HttpStatus.UNAUTHORIZED);
			
		} else {
			
			if (!authService.isAdmin(token)) {
				
				return new ResponseEntity<List<BetPurchase>>(HttpStatus.FORBIDDEN);
				
			} else {
				
				String userId = authService.getUserId(token);
				List<BetPurchase> purchases = bettingService.getPurchases();
				return new ResponseEntity<List<BetPurchase>>(purchases, HttpStatus.OK);
				
			}
		}

	}
	
	@RequestMapping("/betting/paypal/confirm")
	public @ResponseBody String confirmPaypal(
			@RequestParam(value="PayerID", required=true) String payerId,
			@RequestParam(value="id", required=true) String confirmId,
			@RequestParam(value="token", required=false) String token,
			HttpServletResponse response) {
		
		paymentService.confirmPaypalPurchase(payerId, confirmId);
		return "";
	}
	
	@RequestMapping("/betting/paypal/cancel")
	public @ResponseBody String cancelPaypal(
			@RequestParam(value="id", required=true) String cancelId,
			@RequestParam(value="token", required=false) String token,
			HttpServletResponse response) {
		
		paymentService.cancelPaypalPurchase(cancelId);
		return "";
	}
	
	@RequestMapping("/betting/getRecoveryData")
	public @ResponseBody ResponseEntity<List<Object>> recoveryData(
			@RequestParam(value="token", required = true) String token,
			@RequestParam(value="status_checked", required = false, defaultValue="false") Boolean statusChecked,
			@RequestParam(value="status", required=false, defaultValue="") String status,
			@RequestParam(value="user_checked", required = false, defaultValue="false") Boolean userChecked,
			@RequestParam(value="user_values[]", required = false, defaultValue="0") Integer[] userValues,
			@RequestParam(value="date_checked", required = false, defaultValue="false") Boolean dateChecked,
			@RequestParam(value="date_values_end", required = false, defaultValue="") String dateValuesEnd,
			@RequestParam(value="date_values_start", required = false, defaultValue="") String dateValuesStart,
			HttpServletResponse response) {
		
		if (!this.authService.validSession(token)) {
			return new ResponseEntity<List<Object>>(HttpStatus.UNAUTHORIZED);
		} else {
			if (!this.authService.isAdmin(token)) {
				return new ResponseEntity<List<Object>>(HttpStatus.FORBIDDEN);
			} else {
				List<Object> recoveryData = this.bettingService.getRecoveryData(statusChecked, status, userChecked, userValues, dateChecked, dateValuesEnd, dateValuesStart);
				return new ResponseEntity<List<Object>>(recoveryData, HttpStatus.OK);
//				return null;
			}
		}
		
	}
	
	@RequestMapping("/betting/getTransactionLog")
	public @ResponseBody ResponseEntity<List<Object>> transactionLog(
			@RequestParam(value="token", required = true) String token,
			HttpServletResponse response) {
		
		if (!this.authService.validSession(token)) {
			return new ResponseEntity<List<Object>>(HttpStatus.UNAUTHORIZED);
		} else {
			if (!this.authService.isAdmin(token)) {
				return new ResponseEntity<List<Object>>(HttpStatus.FORBIDDEN);
			} else {
//				AnywhereDB db = new AnywhereDB();
				List<Object> transactionData = this.anywhereDB.getTransactionLog();
				return new ResponseEntity<List<Object>>(transactionData, HttpStatus.OK);
//				return null;
			}
		}
		
	}
	
	@RequestMapping("/betting/getPurchaseLog")
	public @ResponseBody ResponseEntity<List<Object>> purchaseLog(
			@RequestParam(value="token", required = true) String token,
			HttpServletResponse response) {
		
		if (!this.authService.validSession(token)) {
			return new ResponseEntity<List<Object>>(HttpStatus.UNAUTHORIZED);
		} else {
			if (!this.authService.isAdmin(token)) {
				return new ResponseEntity<List<Object>>(HttpStatus.FORBIDDEN);
			} else {
//				AnywhereDB db = new AnywhereDB();
				List<Object> purchaseData = this.anywhereDB.getPurchaseLog();
				return new ResponseEntity<List<Object>>(purchaseData, HttpStatus.OK);
//				return null;
			}
		}
		
	}
	
	@RequestMapping("/betting/getActivePurchaseLog")
	public @ResponseBody ResponseEntity<List<Object>> activePurchaseLog(
			@RequestParam(value="token", required = true) String token,
			HttpServletResponse response) {
		
		if (!this.authService.validSession(token)) {
			return new ResponseEntity<List<Object>>(HttpStatus.UNAUTHORIZED);
		} else {
			if (!this.authService.isAdmin(token)) {
				return new ResponseEntity<List<Object>>(HttpStatus.FORBIDDEN);
			} else {
//				AnywhereDB db = new AnywhereDB();
				List<Object> purchaseData = this.anywhereDB.getActivePurchaseLog();
				return new ResponseEntity<List<Object>>(purchaseData, HttpStatus.OK);
//				return null;
			}
		}
		
	}
	
	@RequestMapping("/betting/getCurrentPurchaseLog")
	public @ResponseBody ResponseEntity<List<Object>> currentPurchaseLog(
			@RequestParam(value="token", required = true) String token,
			HttpServletResponse response) {
		
		if (!this.authService.validSession(token)) {
			return new ResponseEntity<List<Object>>(HttpStatus.UNAUTHORIZED);
		} else {
			if (!this.authService.isAdmin(token)) {
				return new ResponseEntity<List<Object>>(HttpStatus.FORBIDDEN);
			} else {
//				AnywhereDB db = new AnywhereDB();
				List<Object> purchaseData = this.anywhereDB.getCurrentPurchaseLog();
				return new ResponseEntity<List<Object>>(purchaseData, HttpStatus.OK);
//				return null;
			}
		}
		
	}
	
	@RequestMapping("/betting/lottoGameSell")
	public @ResponseBody ResponseEntity<Object> lottoGameSell(
			@RequestParam(value="token", required = true) String token,
			@RequestParam(value="lottoGame", required = true) String lottoGame,
			@RequestParam(value="drawDay", required = true) Date drawDay,
			@RequestParam(value="numberOfDraws", required = true) Integer numberOfDraws,
			@RequestParam(value="numberOfBetlines", required = true) Integer numberOfBetlines,
			@RequestParam(value="betlinesData", required = true) List<Object> betlinesData,
			HttpServletResponse response) {
		return null;
	}
	
	@RequestMapping("/betting/pending")
	public @ResponseBody ResponseEntity<Object> pendingBets(
			@RequestParam(value="token", required = true) String token,
			HttpServletResponse response) {
		return bettingService.listPendingByUser(token);
	}
	
	@RequestMapping("/betting/pending/cost")
	public @ResponseBody ResponseEntity<Object> pendingBetsCost(
			@RequestParam(value="token", required = true) String token,
			HttpServletResponse response) {
		return bettingService.pendingBetsCost(token);
	}
}