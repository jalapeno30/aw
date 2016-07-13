'use strict';

angular.module('lotteryApp')
  .service('Main', function Main() {
    // AngularJS will instantiate a singleton by calling "new" on this function
    var heading = "Home"

    var self = this;

    self.getHeading = function() {
    	return heading;
    }
    self.setHeading = function(name) {
    	heading = name;
    }

  });
