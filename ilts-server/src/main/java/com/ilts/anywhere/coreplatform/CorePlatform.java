package com.ilts.anywhere.coreplatform;

import com.ilts.anywhere.betting.PurchaseWrapper;
import com.ilts.anywhere.coreplatform.lotto.LottoGameSellRequest;
import com.ilts.anywhere.coreplatform.lotto.LottoSelection;
import com.ilts.anywhere.coreplatform.lotto.OSS;
import com.ilts.anywhere.datastore.AnywhereDB;
import com.ilts.anywhere.payment.BetPurchase;
import com.ilts.anywhere.response.Response;
import com.ilts.anywhere.response.ResponseFactory;
import com.ilts.anywhere.response.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@Service
@ComponentScan
public class CorePlatform {

	@Autowired
	private AnywhereDB anywhereDB;

	public boolean isValidSession(String userID, String sessionToken) {
		
//		return this.authService.validSession(sessionToken);
		return true;
		
	}

	public Response placeBet(ArrayList<Integer> orderId, String date) {
            return null;
		
		// persist data here
//		try {
//			
//			this.anywhereDB.placeBet(orderId, date);
//			
//			return ResponseFactory.makeResponse(ResponseType.SUCCESSBETPLACE);
//			
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//			return ResponseFactory.makeResponse(ResponseType.ERROR);
//			
//		}
//		
	}

	public Response saveTicketInfo(String ticketIdentifierNumber, String winningsMethod, String userID) {
            return null;

//		return ResponseFactory.makeResponse(ResponseType.SUCCESSSAVETICKET);

	}

	public Response cancelBet(String userID, String betConfirmationID) {
            return null;

//		return ResponseFactory.makeResponse(ResponseType.SUCCESSCANCELBET);

	}
	
	public List<BetPurchase> getPurchases() {
		return this.anywhereDB.getPurchases();
	}
	
	public List<PurchaseWrapper> getPurchases(String userId) {
		return this.anywhereDB.getPurchases(userId);
	}

	public List<BetPurchase> getPurchases(Boolean amountChecked,
			String amountCondition, Double amountValuesFirst,
			Double amountValuesSecond, Boolean dateChecked,
			String dateValuesEnd, String dateValuesStart,
			Boolean statusChecked, String[] statusValues, Boolean userChecked,
			Integer[] userValues) throws ParseException {
		
		return this.anywhereDB.getPurchases(amountChecked,
			amountCondition, amountValuesFirst,
			amountValuesSecond, dateChecked,
			dateValuesEnd, dateValuesStart,
			statusChecked, statusValues, userChecked,
			userValues);
	}

	public void confirmPurchase(String confirmId) {
		System.out.println("Confirmation ID: " + confirmId);
		this.anywhereDB.confirmPurchase(confirmId);
		
	}

	public List<Object> getRecoveryData() {
		
		return this.anywhereDB.getRecoveryData();
		
	}

	public List<Object> getRecoveryData(Boolean statusChecked, String status,
			Boolean userChecked, Integer[] userValues, Boolean dateChecked, String dateValuesEnd, String dateValuesStart) {
		return this.anywhereDB.getRecoveryData(statusChecked, status, userChecked, userValues, dateChecked, dateValuesEnd, dateValuesStart);
	}
	
	public Object lottoGameSell(String lottoGame, String drawDay, Integer numberOfDraws, Integer numberOfBetlines, String selectionType, Integer numberOfSelections, List<List<Integer>> selections, Integer bonusSelection) {
		
		// build request object
		
		// build lottoGameRequest
		LottoGameSellRequest lottoRequest = new LottoGameSellRequest();
		lottoRequest.setLottoGame(lottoGame);
		lottoRequest.setDrawDay(drawDay);
		lottoRequest.setNumberOfDraws(numberOfDraws);
		
		List<LottoSelection> selectionsArray = new ArrayList<LottoSelection>();
		
		// populate selectionsArray
		for (List<Integer> selection : selections) {
			selectionsArray.add(OSS.processSelection(selection));
		}

		lottoRequest.setSelection(selectionsArray);
		
		// send request to OSS
		return OSS.lottoGameSell(lottoRequest);

	}

//	public Double calculateBetCost() {
//		
//		return oss.calculateBetCost();
//	}

}