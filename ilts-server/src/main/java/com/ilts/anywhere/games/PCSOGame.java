package com.ilts.anywhere.games;

import java.math.BigDecimal;
import java.util.List;

public class PCSOGame extends Game {
	
	PCSOGame() {
		super(GameType.PCSO);
		construct();
	}

	@Override
	protected void construct() {
		// TODO Auto-generated method stub

	}
	
	private int numbers;
	private BigDecimal cost;
	private String logo;
//	private Draw[] draws;
	private List<PCSODraw> draws;
	
	public int getNumbers() {
		return this.numbers;
	}
	
	public void setNumbers(int numbers) {
		this.numbers = numbers;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public List<PCSODraw> getDraws() {
		return draws;
	}

	public void setDraws(List<PCSODraw> draws) {
		this.draws = draws;
	}
	
	

}
