'use strict';

angular.module('lotteryApp')
  .controller('GameCtrl', function ($scope, Game, Games, LottoGameFactory) {
    // $scope.draws = [
    // 	{
    // 		"image" : Game.getGameLogo(),
    // 		"jackpot" : '$4 Million (est)',
    // 		"drawDate" : '12 Apr 2014',
    // 		"drawDay" : 'Tonight',
    // 		"drawCode" : 'Draw 1234'
    // 	},
    // 	{
    // 		"image" : Game.getGameLogo(),
    // 		"jackpot" : '$4 Million (est)',
    // 		"drawDate" : '19 Apr 2014',
    // 		"drawDay" : 'Next week',
    // 		"drawCode" : 'Draw 1235'
    // 	}
    // ];

    var self = this;
    var game = LottoGameFactory.create();

    // self.checkedDraws = [];
    // self.selectedDraws = [];

    $scope.init = function(args) {
        game.init(args);
        self.id = game.getID();
        self.title = game.getTitle();
        self.numbers = game.getNumbers();
        self.draws = game.getDraws();
        self.multiplier = game.getMultiplier();
        self.logo = game.getLogo();
        self.game = game;


        // pre-select first draw in game
        for (var i = 0; i < self.draws.length; i++) {
            if (i == 0) {
                self.draws[i].selected = true;
            } else {
                self.draws[i].selected = false;
            }
        }
    }

    var redo = function(game) {
        self.id = game.getID();
        self.title = game.getTitle();
        self.numbers = game.getNumbers();
        self.draws = game.getDraws();
        self.multiplier = game.getMultiplier();
        self.logo = game.getLogo();
        // self.game = game;
    }

    $scope.$watch(function(){
        return self.game;
    }, function(newVal, oldVal){
        redo(newVal);
    }, true);

    // $scope.id = game.


    // $scope.draws = Game.getAvailableDraws();

    // $scope.$watch(function(){
    //     return Game.getAvailableDraws();   
    // }, function(newVal, oldVal){
    //     $scope.draws = newVal;
    // });

    // $scope.checkedDraws = [];
    // $scope.selectedDraws = [];

    // for (var i = 0; i < $scope.draws.length; i++) {
    //     if (i == 0) {
    //         $scope.checkedDraws.push(true);
    //     } else {
    //         $scope.checkedDraws.push(false);
    //     }
    // }
    // $scope.selectedDraws = [];
    // for(var i = 0; i < $scope.draws.length; i++) {
    //     if ($scope.checkedDraws[i] == true) {
    //         $scope.selectedDraws.push($scope.draws[i]);
    //     }
    // }
    // Game.setDraws($scope.selectedDraws);

    // $scope.selectDraws = function() {
    //     $scope.selectedDraws = [];
    //     for(var i = 0; i < $scope.draws.length; i++) {
    //         if ($scope.checkedDraws[i] == true) {
    //             $scope.selectedDraws.push($scope.draws[i]);
    //         }
    //     }
    //     Game.setDraws($scope.selectedDraws);
    // }
    // $scope.getGameLogo= function() {
    //     return Game.getGameLogo();
    // }
  });


