'use strict';

angular.module('lotteryApp')
  .service('AngularLoaderBlocker', function AngularLoaderBlocker() {
    // AngularJS will instantiate a singleton by calling "new" on this function
    var self = this;

    self.enabled = false;

    self.enable = function() {
      self.enabled = true;
    }

    self.disable = function() {
      self.enabled = false;
    }

    self.isEnabled = function() {
      return self.enabled;
    }
  });
