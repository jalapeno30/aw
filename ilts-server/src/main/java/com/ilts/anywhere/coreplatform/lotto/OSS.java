package com.ilts.anywhere.coreplatform.lotto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ilts.anywhere.coreplatform.utility.CPUtility;
import com.ilts.anywhere.coreplatform.utility.DrawDay;
import com.ilts.anywhere.coreplatform.utility.SelectionType;

public class OSS {
	
	/**
	 * receive bet details then return ticket serial number and ticket cost
	 * @param LottoGameSellRequest
	 * @return LottoGameSellResponse
	 */
	public static LottoGameSellResponse lottoGameSell(LottoGameSellRequest lottoRequest) {
		
		
		//
		// Implement your logic here
		//
		
		// call API
		return mockAPIResponse(lottoRequest);
	}
	
	private static LottoGameSellResponse mockAPIResponse(
			LottoGameSellRequest request) {
		
		// process response
		final Date timeStampOSS = new Date(System.currentTimeMillis());
		final Integer numberOfDrawsOSS = request.getNumberOfDraws();
		final ArrayList<String> drawsOSS = new ArrayList<String>(numberOfDrawsOSS);
		for (int i = 0; i < numberOfDrawsOSS; i++) {
			drawsOSS.add(timeStampOSS.toString() + ":000" + i);
		}
		LottoGameSellResponse lottoResponse = new LottoGameSellResponse();
		lottoResponse.setDrawList(drawsOSS);
		lottoResponse.setNumberOfDraws(numberOfDrawsOSS);
		lottoResponse.setTimestamp(timeStampOSS);
		lottoResponse.setTicketCost(1.0);
		lottoResponse.setTicketTax(0.8);
		lottoResponse.setTsn("0123-4567-89AB-CDEF");
		return lottoResponse;
	}

	/**
	 * takes in the selection and parses it into a lottoSelection object
	 * @param selection
	 * @return
	 */
	public static LottoSelection processSelection(List<Integer> selection) {
		LottoSelection lottoSelection = new LottoSelection();
		
		lottoSelection = randomSelection(lottoSelection);
		
		return lottoSelection;
	}


	private static LottoSelection randomSelection(LottoSelection lottoSelection) {
		Integer max = 4;
		Integer min = 1;
		
		Random random = new Random();
		int randomNumber = random.nextInt(max - min) + min;
		
		switch(randomNumber) {
			
			case 1:
				//
				// Player Pick, Straight = 1 5 8 11 14 20, Bonus = 25 
				//
				{	
					final List<String> types = new ArrayList<String>(1);
					types.add(CPUtility.selectionTypeToString(SelectionType.STRAIGHT));
					final List<Integer> numbersIn = new ArrayList<Integer>(6);
					numbersIn.add(1);
					numbersIn.add(5);
					numbersIn.add(8);
					numbersIn.add(11);
					numbersIn.add(14);
					numbersIn.add(20);
					lottoSelection.setTypes(types);
					lottoSelection.setNumbers(numbersIn);
					lottoSelection.setBonusNumber(random.nextInt(40 - 1) + 1);
				}
				break;
			case 2:
				//
				// Player Pick, LottoSystem 7 = 11 12 13 14 15 16 17, no Bonus
				//
				{
					final List<String> typesIn = new ArrayList<String>(1);
					typesIn.add(CPUtility.selectionTypeToString(SelectionType.SYSTEM) + "7");
					final List<Integer> numbersIn = new ArrayList<Integer>(7);
					numbersIn.add(11);
					numbersIn.add(12);
					numbersIn.add(13);
					numbersIn.add(14);
					numbersIn.add(15);
					numbersIn.add(16);
					numbersIn.add(17);
					lottoSelection.setTypes(typesIn);
					lottoSelection.setNumbers(numbersIn);
				}
				break;
			case 3:
				//
				// Quick Pick 
				//
				{
					final List<String> typesIn = new ArrayList<String>(1);
					typesIn.add(CPUtility.selectionTypeToString(SelectionType.QUICK_PICK));
					lottoSelection.setTypes(typesIn);
				}
				break;
			case 4:
				//
				// Quick Pick, LottoSystem 8
				//
				{
					final List<String> typesIn = new ArrayList<String>(1);
					typesIn.add(CPUtility.selectionTypeToString(SelectionType.QUICK_PICK));
					typesIn.add(CPUtility.selectionTypeToString(SelectionType.SYSTEM) + 8);
					lottoSelection.setTypes(typesIn);
				}
				break;
		}
		
		return lottoSelection;
	}

// Deprecated API call
/*


	public Object lottoGameSell(String lottoGame, String drawDay, Integer numberOfDraws, final List<Object> selections) {
	
		final Date timeStampOSS = new Date(LottoSystem.currentTimeMillis());
		final Integer numberOfDrawsOSS = numberOfDraws;
		final ArrayList<String> drawsOSS = new ArrayList<String>(numberOfDrawsOSS);
		for (int i = 0; i < numberOfDrawsOSS; i++) {
			drawsOSS.add(timeStampOSS.toString() + ":000" + i);
		}

		return new Object() {
			public Date timeStamp = timeStampOSS;
			public Integer numberOfDraws = numberOfDrawsOSS;
			public List<String> drawList = drawsOSS;
			public String tsn = "0123-4567-89AB-CDEF";
			public Double ticketCost = 1.0;
			public Double ticketTax = 0.8;
		};
	}
*/
	public static void main(String [ ] args) {
		
		
		String lottoGame = CPUtility.selectionTypeToString(SelectionType.QUICK_PICK);
		String drawDay = CPUtility.drawDayToString(DrawDay.MON);
		int numberOfDraws = 3;
		int val = 3;
		List<List<Integer>> selections = new ArrayList<List<Integer>>(6);

		// Creating the selection object details
		for (int i = 0; i < 6; i++) {
			ArrayList<Integer> newObj = new ArrayList<Integer>(6);
			for (int j = 0; j < 6; j++) {
				newObj.add(val++);
			}
			selections.add(newObj);
		}
		
		// build lottoGameRequest
		LottoGameSellRequest lottoRequest = new LottoGameSellRequest();
		lottoRequest.setLottoGame(lottoGame);
		lottoRequest.setDrawDay(drawDay);
		lottoRequest.setNumberOfDraws(numberOfDraws);
		
		List<LottoSelection> selectionsArray = new ArrayList<LottoSelection>();
		// populate selectionsArray
		for (List<Integer> selection : selections) {
			selectionsArray.add(processSelection(selection));
		}

		lottoRequest.setSelection(selectionsArray); 

		
	}
	
}
