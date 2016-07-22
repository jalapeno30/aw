'use strict';

angular.module('lotteryApp')
  .controller('BetGameCtrl', function ($scope, betGame) {

        var self = this;

        var gameObject = {};
        self.game = {};
        self.draws = [];
        self.system;

        self.init = function(game) {
            gameObject = new betGame();
            gameObject.initialize(game);
            self.game = gameObject.getGame();
            gameObject.setSystem("6");
            gameObject.retrieveDraws();
        };

        $scope.$watch(function(){
            return gameObject.getDraws();
        }, function(data){
            self.draws = data;
        });

        $scope.$watch(function(){
            return gameObject.getSystem();
        }, function(data){
            self.system = data;
        });
  });
