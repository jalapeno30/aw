/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: StandardGame.java
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilts.anywhere.games;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author ssanapureddy
 */
public class StandardGame extends Game{
    StandardGame() {
        super(GameType.STANDARD);
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
    private List<StandardDraw> draws;

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

    public List<StandardDraw> getDraws() {
        return draws;
    }

    public void setDraws(List<StandardDraw> draws) {
        this.draws = draws;
    }
}