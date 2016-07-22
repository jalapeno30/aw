'use strict';

angular.module('lotteryApp')
  .filter('languageKeys', function () {
    return function (input) {
      return input
        .toLowerCase()
        .replace(/_/g, ' ')
        .replace(/(?:^|\s)\S/g, function(a) {
          return a.toUpperCase();
        });;
    };
  });
