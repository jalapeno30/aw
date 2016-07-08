package com.ilts.anywhere.games;

import java.util.List;
import java.util.ArrayList;

public class GameWrapper {

	private String name;
	private int numbers;
	private int cost;
	private String logo;
	private List<DrawWrapper> draws = new ArrayList<DrawWrapper>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumbers() {
		return numbers;
	}
	public void setNumbers(int numbers) {
		this.numbers = numbers;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public List<DrawWrapper> getDraws() {
		return draws;
	}
	public void addDraw(DrawWrapper draw) {
		this.draws.add(draw);
	}
}
