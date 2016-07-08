package com.ilts.anywhere.cart;

//import java.util.List;
import java.util.ArrayList;

public class NewOrderWrapper {
	
	private String gameID;
	private String drawID;
	private String system;
	private ArrayList<ArrayList<Integer>> numbers = new ArrayList<ArrayList<Integer>>();
	private int cost;
	private String token;
	private String userId;
	
	public String getGameID() {
		return gameID;
	}
	public void setGameID(String gameID) {
		this.gameID = gameID;
	}
	public String getDrawID() {
		return drawID;
	}
	public void setDrawID(String drawID) {
		this.drawID = drawID;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public ArrayList<ArrayList<Integer>> getNumbers() {
		return numbers;
	}
	public void setNumbers(ArrayList<ArrayList<Integer>> numbers) {
		this.numbers = numbers;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
