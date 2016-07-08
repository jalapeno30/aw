package com.ilts.anywhere.betting;

public class BetFactory {

	public static OBet makeBet(BetType betType) {
		OBet bet = null;
		switch (betType) {
			case PCSO:
				bet = new PCSOBet();
				break;
			default:
				break;
		}
		return bet;
	}

}