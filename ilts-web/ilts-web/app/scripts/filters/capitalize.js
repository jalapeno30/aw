'use strict';

angular.module('lotteryApp')
  .filter('capitalize', function () {
    return function (input) {
      return input.substring(0,1).toUpperCase()+input.substring(1);
    };
  });
