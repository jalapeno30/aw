package com.ilts.anywhere.coreplatform.lotto;

import java.util.List;

public class LottoGameSellRequest {
	private String lottoGame;
	private String drawDay;
	private Integer numberOfDraws;
	private List<LottoSelection> selections;
	
	public String getLottoGame() {
		return lottoGame;
	}
	public void setLottoGame(String lottoGame) {
		this.lottoGame = lottoGame;
	}
	public String getDrawDay() {
		return drawDay;
	}
	public void setDrawDay(String drawDay) {
		this.drawDay = drawDay;
	}
	public Integer getNumberOfDraws() {
		return numberOfDraws;
	}
	public void setNumberOfDraws(Integer numberOfDraws) {
		this.numberOfDraws = numberOfDraws;
	}
	public List<LottoSelection> getSelections() {
		return selections;
	}
	public void setSelection(List<LottoSelection> selections) {
		this.selections = selections;
	}
	
	
}
