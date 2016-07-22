'use strict';

angular.module('lotteryApp')
  .controller('NumberCtrl', function ($scope, Game, Orders, $location, $anchorScroll, OrderFactory, LottoGameFactory, growl) {

    var gameObj = $scope.$parent.game.game;

    $scope.$watch(function(){
      return gameObj;
    }, function(newVal, oldVal){
      redo(newVal);
    }, true);

    var redo = function(game) {
      $scope.maxSets = game.getMaxSets();//6;
      $scope.activeSet = game.getActiveSet();//0;
      $scope.selectedNumbers = game.getSelectedNumbers();//[];
      $scope.numbers = game.getNumberSet();//Game.getNumberSet();
      $scope.numberList = game.getNumbers();//Game.getNumbers();
      $scope.selectableNumbers = game.getSelectableNumbers();
      $scope.gameType = game.getTitle();//Game.getGameType();

      if ($scope.selectableNumbers < $scope.selectedNumbers[$scope.activeSet].length) {
        var deficit = $scope.selectedNumbers[$scope.activeSet].length - $scope.selectableNumbers;
        for (var i = 0; i < deficit; i++) {
          $scope.selectedNumbers[$scope.activeSet].pop();
        }
      }
      $scope.completeNumberSet = ($scope.selectableNumbers == $scope.selectedNumbers[$scope.activeSet].length);
    }

    $scope.maxSets = gameObj.getMaxSets();//6;
    $scope.activeSet = gameObj.getActiveSet();//0;
  	$scope.selectedNumbers = gameObj.getSelectedNumbers();//[];
  	$scope.numbers = gameObj.getNumberSet();//Game.getNumberSet();
    $scope.numberList = gameObj.getNumbers();//Game.getNumbers();
    $scope.selectableNumbers = gameObj.getSelectableNumbers();
    $scope.completeNumberSet = false;
    $scope.gameType = gameObj.getTitle();//Game.getGameType();
    $scope.luckyPicks = [];
    // console.log(gameObj.getNumbers());

    $scope.setLabels = Game.getSetLabels();

    for (var i = 0; i < $scope.maxSets; i++) {
      $scope.selectedNumbers.push([]);
      $scope.luckyPicks.push(false);
    }

    // $scope.$watch(function(){
    //   return Game.getSelectableNumbers()
    //  }, function(data){
    //   $scope.selectableNumbers = data;
    //   if ($scope.selectableNumbers < $scope.selectedNumbers[$scope.activeSet].length) {
    //     var deficit = $scope.selectedNumbers[$scope.activeSet].length - $scope.selectableNumbers;
    //     for (var i = 0; i < deficit; i++) {
    //       $scope.selectedNumbers[$scope.activeSet].pop();
    //     }
    //   }
    //   $scope.completeNumberSet = ($scope.selectableNumbers == $scope.selectedNumbers[$scope.activeSet].length);
    // });

    // $scope.init = function(number, game) {
    //   Game.setNumbers(number);
    //   $scope.numbers = Game.getNumberSet();
    //   $scope.game = game;
    // }

    $scope.rangeSets = function() {
      return new Array($scope.maxSets);
    }

    $scope.toggleSet = function(number) {
      gameObj.setActiveSet(number);//$scope.activeSet = number;
    }

  	$scope.toggleNumber = function(number) {
  		var newArray = [];
  		// check if in selectedNumbers
  		var inArrayCheck = $scope.selectedNumbers[$scope.activeSet].indexOf(number);

  		if (inArrayCheck>-1) {
  		// number is in array
  			for (var i=0; i < $scope.selectedNumbers[$scope.activeSet].length; i++) {
  				if (i != inArrayCheck) {
  					newArray.push($scope.selectedNumbers[$scope.activeSet][i]);
  				}
  			}
  			// $scope.selectedNumbers[$scope.activeSet] = newArray;
        gameObj.selectedNumbers[$scope.activeSet()] = newArray;

        $scope.completeNumberSet = false;
  		} else {
  			// number is not in array
  			if ($scope.selectedNumbers[$scope.activeSet].length < $scope.selectableNumbers) {
          gameObj.selectedNumbers[$scope.activeSet].push(number);
		  		// $scope.selectedNumbers[$scope.activeSet].push(number);
		  		// $scope.selectedNumbers[$scope.activeSet].sort(function(a,b){return a - b});
          gameObj.selectedNumbers[$scope.activeSet].sort(function(a,b){return a - b});
          // console.log($scope.selectedNumbers[$scope.activeSet]);
	  		}
        $scope.completeNumberSet = ($scope.selectableNumbers == $scope.selectedNumbers[$scope.activeSet].length);
  		}
  	}

    $scope.getSelectableNumbers = function() {
      return Array($scope.selectableNumbers);
    }

    $scope.luckyPick = function() {
      // $scope.selectedNumbers[$scope.activeSet] = randomNumberSet();
      gameObj.selectedNumbers[$scope.activeSet] = randomNumberSet();
      $scope.luckyPicks[$scope.activeSet] = true;
      $scope.completeNumberSet = true;
    }

    $scope.unlucky = function(number) {
      $scope.luckyPicks[number] = false;
      $scope.selectedNumbers[number] = [];
    }

    $scope.autoPlay = function() {
      // $scope.selectedNumbers[$scope.activeSet] = randomNumberSet();
      gameObj.selectedNumbers[$scope.activeSet] = randomNumberSet();
      $scope.luckyPicks[$scope.activeSet] = false;
      $scope.completeNumberSet = ($scope.selectableNumbers == $scope.selectedNumbers[$scope.activeSet].length);
    }

    function randomNumberSet() {
      var i, arr = [];
      var max = $scope.numberList;
      for (i = 0; i < max; i++) {
          arr[i] = i + 1;
      }

      var shuffled = shuffle(arr);
      return shuffled.slice(0, $scope.selectableNumbers).sort(function(a,b){return a - b});
    }

    function shuffle(array) {
      var currentIndex = array.length
        , temporaryValue
        , randomIndex
        ;

      // While there remain elements to shuffle...
      while (0 !== currentIndex) {

        // Pick a remaining element...
        randomIndex = Math.floor(Math.random() * currentIndex);
        currentIndex -= 1;

        // And swap it with the current element.
        temporaryValue = array[currentIndex];
        array[currentIndex] = array[randomIndex];
        array[randomIndex] = temporaryValue;
      }

      return array;
    }

    $scope.addOrder = function() {
      var draws = gameObj.getSelectedDraws();//Game.getDraws();

      if (draws.length === 0) {
        // $scope.clearAlerts();
        // $scope.addAlert('You must select at least one draw', 'danger');
        //
        // $location.hash('navigation');
        // $anchorScroll();
        growl.addErrorMessage('You must select at least one draw');

      } else {
        $scope.clearAlerts();

        for (var i = 0; i < draws.length; i++) {
          var order = OrderFactory.create(gameObj, draws[i].id);

          Orders.addPendingOrder(order);
        }

        Orders.sendPendingOrders();
        Orders.refreshOrders();

        // var order = {};

        // console.log(Game);


        // order.game = Game.getGameType();


        // order.draws = [];
        // for (var i = 0; i < draws.length; i++) {
        //   order.draws.push({
        //     "date": draws[i].drawDate,
        //     "id": draws[i].id
        //   });
        // }

        // order.system = Game.getSystem();

        // // add selected numbers
        // var temp = [];
        // var setCount = 0;
        // for (var i = 0; i < $scope.selectedNumbers.length; i++) {
        //   if ($scope.selectedNumbers[i].length == $scope.selectableNumbers) {
        //     // temp.push($scope.selectedNumbers[i]);
        //     temp.push({id: i, label: $scope.setLabels[i], numbers: $scope.selectedNumbers[i], lucky: $scope.luckyPicks[i]})
        //     setCount++;
        //   } else {
        //     temp.push({id: i, label: $scope.setLabels[i], numbers: [], lucky: $scope.luckyPicks[i]})
        //   }
        // }
        // order.numbers = temp;

        // // console.log(Game.getTotalMultiplier());
        // order.amount = Game.getTotalMultiplier() * setCount;//order.numbers.length;

        // // add order
        // Orders.addOrder(order);

        // clear selected numbers
        $scope.selectedNumbers = [];
        for (var i = 0; i < $scope.maxSets; i++) {
          $scope.selectedNumbers.push([]);
          $scope.luckyPicks = false;
        }

        // hide add to orders button
        $scope.completeNumberSet = false;
      }
    }

  });
