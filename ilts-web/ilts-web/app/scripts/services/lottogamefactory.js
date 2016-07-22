'use strict';

angular.module('lotteryApp')
  .factory('LottoGameFactory', function (LottoGame) {
    return {
      create: function(args) {
        return new LottoGame(args);
      }
    }
  });
