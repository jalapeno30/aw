'use strict';

angular.module('lotteryApp')
  .factory('OrderFactory', function (Order) {
    return {
        create: function(game, drawID) {
          return new Order(game, drawID);
        }
    }
  });
