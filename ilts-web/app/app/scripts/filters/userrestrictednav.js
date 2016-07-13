'use strict';

angular.module('lotteryApp')
  .filter('userRestrictedNav', function () {
    return function (navs, logged) {
      var filteredNavs = [];

      for(var i = 0; i < navs.length; i++) {
      	if (logged) {
      		filteredNavs.push(navs[i]);
      	} else if (!logged && !navs[i].restricted) {
      		filteredNavs.push(navs[i]);
      	}
      }

      return filteredNavs;
    };
  });
