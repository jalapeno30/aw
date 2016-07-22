'use strict';

angular.module('lotteryApp')
  .factory('LottoGame', function () {
    
    var LottoGame = function(gameID) {
    	this.id = gameID;
    	this.draws = [];
    	this.logo = "";
    	this.multiplier = 1;
    	this.numbers = "";
    	this.title = "";
    	this.system = "";
    	this.selectedNumbers = [];
    	this.numberSet = [];
    	this.maxSets = 6;
    	this.activeSet = 0;
    };

    LottoGame.prototype.init = function(arg) {
    	this.id = arg.id
    	this.setTitle(arg.title);
    	this.setNumbers(arg.numbers);
    	this.setMultiplier(arg.multiplier);
    	this.setLogo(arg.logo);
    	this.setDraws(arg.draws);
    }

    LottoGame.prototype.getID = function() {
    	return this.id;
    }

    LottoGame.prototype.getDraws = function() {
    	return this.draws;
    }

    LottoGame.prototype.setDraws = function(draws) {
    	this.draws = draws;
    }

    LottoGame.prototype.getSelectedDraws = function() {
    	var temp = [];
    	for (var i = 0; i < this.draws.length; i++) {
    		if (this.draws[i].selected === true) {
    			temp.push(this.draws[i]);
    		}
    	}
    	return temp;
    }

    LottoGame.prototype.getLogo = function() {
    	return this.logo;
    }

    LottoGame.prototype.setLogo = function(logo) {
    	this.logo = logo;
    }

    LottoGame.prototype.getMultiplier = function() {
    	return this.multiplier;
    }

    LottoGame.prototype.setMultiplier = function(multiplier) {
    	this.multiplier = multiplier;
    }

    LottoGame.prototype.getNumbers = function() {
    	return this.numbers;
    }

    LottoGame.prototype.setNumbers = function(numbers) {
    	this.numbers = numbers;
    }

    LottoGame.prototype.getTitle = function() {
    	return this.title;
    }

    LottoGame.prototype.setTitle = function(title) {
    	this.title = title;
    }

    LottoGame.prototype.getSystem = function() {
    	return this.system;
    }

    LottoGame.prototype.setSystem = function(system) {
    	this.system = system;
    }

    LottoGame.prototype.getSelectableNumbers = function() {
    	return this.system.value;
    }

    LottoGame.prototype.getNumberSet = function() {
    	var numbers = this.numbers;
    	if (numbers == 0 || numbers == "") {
    		return [];
    	} else {
    		var numberSet = [];
    		var tempSet = [];
    		for (var i = 1; i <= numbers; i++) {
	    		tempSet.push(i);
	    		if (i%10 == 0 || i == numbers) {
	    			numberSet.push(tempSet);
	    			tempSet = [];
	    		}
	    	}
	    	return numberSet;
    	}
    }

    LottoGame.prototype.getSelectedNumbers = function() {
    	return this.selectedNumbers;
    }

    LottoGame.prototype.setSelectedNumbers = function(selectedNumbers) {
    	this.selectedNumbers = selectedNumbers;
    }

    LottoGame.prototype.getMaxSets = function() {
    	return this.maxSets;
    }

    LottoGame.prototype.setMaxSets = function(maxSets) {
    	this.maxSets = maxSets;
    }

    LottoGame.prototype.getActiveSet = function() {
    	return this.activeSet;
    }

    LottoGame.prototype.setActiveSet = function(activeSet) {
    	this.activeSet = activeSet;
    }

    // LottoGame.prototype.getCompletedNumberSet = function() {
    // 	return this.completedNumberSet;
    // }

    // LottoGame.prototype.setCompletedNumberSet = function(numberSet) {
    // 	this.completedNumberSet = numberSet;
    // }

    return LottoGame;

  });
