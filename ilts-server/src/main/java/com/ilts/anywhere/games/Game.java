package com.ilts.anywhere.games;

public abstract class Game {
	
	public Game(GameType gameType) {
		this.gameType = gameType;
		processGame();
	}

	private void processGame() {

	}

	protected abstract void construct();
	
	private GameType gameType = null;
	
	private int id;
	private String name;
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

//	public int id;
//	public String name;
//	public int numbers;
//	public int cost;
//	public String logo;
//	public Draw[] draws;
//
//	public Game(int gameId, String gameName, int gameNumbers, int gameCost, String gameLogo) {
//		id = gameId;
//		name = gameName;
//		numbers = gameNumbers;
//		cost = gameCost;
//		logo = gameLogo;
//	}
//
//	public void initDraws(int count) {
//		this.draws = new Draw[count];
//	}
//
//	public void addDraw(Draw draw, int index) {
//		this.draws[index] = draw;
//	}

}