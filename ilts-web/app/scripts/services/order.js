'use strict';

angular.module('lotteryApp')
  .factory('Order', function () {
    var Order = function(game, drawID) {

      if (game != undefined && game != null) {
        this.game = game.getID();
        this.draw = drawID;

        this.system = game.getSystem();
        this.numbers = game.getSelectedNumbers();
      }
    };

    Order.prototype.setGameID = function(gameID) {
      this.game = gameID;
    };

    Order.prototype.getGameID = function() {
      return this.game;
    };

    Order.prototype.setDrawID = function(drawID) {
      this.draw = drawID;
    };

    Order.prototype.getDrawID = function() {
      return this.draw;
    }

    Order.prototype.getNumbers = function() {
      return this.numbers;
    }

    Order.prototype.getSelectedNumbers = function() {
      var tempSet = [];
      for (var i = 0; i < this.numbers.length; i++) {
        if (this.numbers[i].length > 0) {
          tempSet.push(this.numbers[i]);
        }
      }
      return tempSet;
    }

    Order.prototype.setNumbers = function(numbers) {
      this.numbers = numbers;
    }

    Order.prototype.getSystem = function() {
      return this.system;
    }

    Order.prototype.setSystem = function(system) {
      this.system = system;
    }

    return Order;
  });
