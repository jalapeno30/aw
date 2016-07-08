'use strict';

angular.module('lotteryApp')
  .controller('AppGamesCtrl', function ($scope, $filter, Games, ngTableParams) {

    var self = this;
    self.games = Games.getGames();

    $scope.$watch(function(){
      return Games.getGames();
    }, function(newVals){
      self.games = newVals;
      self.tableParams.reload();
    });

    self.tableParams = new ngTableParams({
      page: 1,
      count: 10,
      sorting: {
        id: 'asc'
      }
    }, {
      total: 0,
      getData: function($defer, params) {
        var orderedData = params.sorting() ? $filter('orderBy')(self.games, params.orderBy()) : self.games;
        params.total(self.games.length);
        $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
      }
    });

  });
