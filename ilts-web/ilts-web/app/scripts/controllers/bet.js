'use strict';

angular.module('lotteryApp')
  .controller('BetCtrl', function ($scope, Games) {
        var self = this;

        self.games = [];
        self.systems = [];

        Games.refreshGames();
        Games.refreshSystems();

        $scope.$watch(function(){
            return Games.getGames();
        }, function(data){
            self.games = data;
        });

        $scope.$watch(function(){
            return Games.getSystems();
        }, function(data){
            self.systems = data;
        });
  });
