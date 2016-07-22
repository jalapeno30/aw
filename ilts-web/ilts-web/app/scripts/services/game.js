'use strict';

angular.module('lotteryApp')
  .service('Game', function Game() {
  	var self = this;
    var numbers = "";
    var numberSet = [];
    var totalNumbers = 55;
    var selectableNumbers = "";
    var gameType = "Grand Lotto 6/55";
    var gameLogo = "images/ignore/grand-lotto.jpg";
    var gameMultiplier = 20;
    var drawMultiplier = 0;
    var systemMultiplier = 1;
    var pickMultiplier = 0;
    var draws = [];
    var availableDraws = [];
    var system = "6";
    var setLabels = ["A", "B", "C", "D", "E", "F"];

    var tempSet = [];

    var productRange = function(a,b) {
	  var product=a,i=a;
	 
	  while (i++<b) {
	    product*=i;
	  }
	  return product;
	}
	 
	var combinations = function(n,k) {
	  if (n==k) {
	    return 1;
	  } else {
	    k=Math.max(k,n-k);
	    return productRange(k+1,n)/productRange(1,n-k);
	  }
	}

	self.getNumbers = function() {
		return numbers;
	}

	self.setNumbers = function(val) {
		numbers = val;
		numberSet = [];
    	tempSet = [];
    	for (var i = 1; i <= numbers; i++) {
    		tempSet.push(i);
    		if (i%10 == 0 || i == numbers) {
    			numberSet.push(tempSet);
    			tempSet = [];
    		}
    	}
	}

	self.getNumberSet = function() {
		return numberSet;
	}

	self.setSelectableNumbers = function(num) {
		selectableNumbers = num;
	}

	self.getSelectableNumbers = function() {
		return selectableNumbers;
	}

	self.setGameType = function(type) {
		gameType = type;
	}

	self.getGameType = function() {
		return gameType;
	}

	self.setGameMultiplier = function(mult) {
		gameMultiplier = mult;
	}

	self.getGameMultiplier = function() {
		return gameMultiplier;
	}

	self.setTotalNumbers = function(num) {
		totalNumbers = num;
	}

	self.setDraws = function(arr) {
		draws = arr;
		drawMultiplier = draws.length;
	}

	self.getDraws = function() {
		return draws;
	}

	self.setAvailableDraws = function(arr) {
		availableDraws = arr;
	}

	self.getAvailableDraws = function() {
		return availableDraws;
	}

	self.setSystem = function(sysName) {
		system = sysName;
		if (sysName == '5R') {
			systemMultiplier = parseInt(totalNumbers) - 5;
		} else {
			systemMultiplier = combinations(parseInt(selectableNumbers), 6);
		}
	}

	self.getSystem = function() {
		return system;
	}

	self.getTotalMultiplier = function() {
		return parseInt(gameMultiplier)*parseInt(drawMultiplier)*parseInt(systemMultiplier);
	}

	self.setGameLogo = function(logo) {
		gameLogo = logo;
	}
	
	self.getGameLogo = function() {
		return gameLogo;
	}

	self.getSetLabels = function() {
		return setLabels;
	}

    return self;
  });
