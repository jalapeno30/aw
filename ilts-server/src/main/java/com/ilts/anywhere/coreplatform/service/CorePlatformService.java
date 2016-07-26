package com.ilts.anywhere.coreplatform.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import com.ilts.anywhere.betting.Purchase;
import com.ilts.anywhere.betting.PurchaseWrapper;
import com.ilts.anywhere.coreplatform.CorePlatform;
import com.ilts.anywhere.logger.TransactionLogger;
import com.ilts.anywhere.payment.BetPurchase;
import com.ilts.anywhere.payment.PaymentService;
import com.ilts.anywhere.response.Response;
import com.ilts.anywhere.response.ResponseService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/*
 * 
 */
@Service
@ComponentScan
public class CorePlatformService {

	@Autowired
	private CorePlatform cp = new CorePlatform();
	private TransactionLogger tl = new TransactionLogger();
	private ResponseService rs = new ResponseService();

	/*
	 * 
	 */
	public Response processBetAction(final ArrayList<String> orderId, String date, String userId) {
                  System.out.println(" +++++++++++++++++ process bet action +++++++++++++++++");     
            System.out.println(">>>>>>>"+orderId);
                        for(int i =0;i<orderId.size();i++)
                        System.out.println("************ >>>>"+orderId.get(i));
                        System.out.println(">>>>>>>"+userId);

		if (!cp.isValidSession(userId, "")) {

			//Invalid Session
			tl.log("Invalid Session");
			tl.logTransaction(userId, "Invalid session", new Object() {
				public ArrayList<String> orders = orderId;
			});

			return rs.errorResponse("Invalid Session");

		} else {

			//Valid Session
			tl.log("Start Bet");
			tl.logTransaction(userId, "Bet Start", new Object() {
				public ArrayList<String> orders = orderId;
			});
			
			PaymentService ps = new PaymentService();
	
			
			// check for funding
			if (ps.hasFunding(orderId, date, userId)) {
				
				final Response response = rs.confirmFundingResponse(ps.getRedirectUri());
				
				tl.logTransaction(userId, "Confirming Funding", new Object() {
					public Response data = response;
				});
				
				return response;
				
			} else {
				
				tl.log("Invalid funds");
				tl.logTransaction(userId, "Invalid funds", new Object() {
					public ArrayList<String> orders = orderId;
				});

				return rs.errorResponse("Invalid Funds");
				
			}
			

		}

	}

	public Response saveTicketInfo(String ticketIdentifierNumber, String winningsMethod, String userID) {

		return cp.saveTicketInfo(ticketIdentifierNumber, winningsMethod, userID);

	}

	public Response cancelBet(String userID, String betConfirmationID) {

		tl.log("Start Cancel Transaction on Bet: " + betConfirmationID);

		Response cancelResponse = cp.cancelBet(userID, betConfirmationID);

		if (cancelResponse.getStatus() == "success") {

			tl.log("Successfully cancelled Transaction on Bet: " + betConfirmationID);

		} else {

			tl.log("Error cancelling Transaction on Bet: " + betConfirmationID);

		}

		return cancelResponse;

	}
	
	public Response requestBetPayout()
	{
		return null;
	}
	
	public List<BetPurchase> getPurchases() {
		
		return cp.getPurchases();
	}
	
	public List<PurchaseWrapper> getPurchases(String userId) {
			System.out.println(">>>>>>>>>>>> List<PurchaseWrapper> getPurchases(String userId) Coreplatform servuicwe **"+userId);

		return cp.getPurchases(userId);
	}

	public List<BetPurchase> getPurchases(Boolean amountChecked,
			String amountCondition, Double amountValuesFirst,
			Double amountValuesSecond, Boolean dateChecked,
			String dateValuesEnd, String dateValuesStart,
			Boolean statusChecked, String[] statusValues, Boolean userChecked,
			Integer[] userValues) throws ParseException {
		
		return cp.getPurchases(amountChecked,
			amountCondition, amountValuesFirst,
			amountValuesSecond, dateChecked,
			dateValuesEnd, dateValuesStart,
			statusChecked, statusValues, userChecked,
			userValues);
	}

	public void confirmPurchase(String confirmId) {
		cp.confirmPurchase(confirmId);
		
	}

	public List<Object> getRecoveryData() {
		
		return cp.getRecoveryData();
		
	}

	public List<Object> getRecoveryData(Boolean statusChecked, String status,
			Boolean userChecked, Integer[] userValues, Boolean dateChecked, String dateValuesEnd, String dateValuesStart) {
		return cp.getRecoveryData(statusChecked, status, userChecked, userValues, dateChecked, dateValuesEnd, dateValuesStart);
	}

	public void lottoGameSell(Purchase purchase, Response cpResponse) {
		System.out.println("-------------- Core PLATFORM lottoGameSell(Purchase purchase, Response cpResponse)");
		// retrieve bet details
		// TODO: parse purchase object
		String lottoGame = "Lotto 6/42";
		String drawDay = "Monday";
		Integer numberOfDraws = 1;
		Integer numberOfBetlines = 2;
		String selectionType = "Player Pick";
		Integer numberOfSelections = 2;
		List<List<Integer>> selections = new ArrayList<List<Integer>>();
		Integer bonusSelection = 9;
		
		List<Integer> selection1 = new ArrayList<Integer>();
		selection1.add(1);
		selection1.add(2);
		selection1.add(5);
		selection1.add(10);
		selection1.add(12);
		selection1.add(20);
		selections.add(selection1);
		
		List<Integer> selection2 = new ArrayList<Integer>();
		selection2.add(21);
		selection2.add(22);
		selection2.add(30);
		selection2.add(32);
		selection2.add(40);
		selection2.add(45);
		selections.add(selection2);

		// pass to facade object
		cp.lottoGameSell(lottoGame, drawDay, numberOfDraws, numberOfBetlines, selectionType, numberOfSelections, selections, bonusSelection);
		
	}

	public Double calculateBetCost() {

		return 10.0;//cp.calculateBetCost();
	}

}