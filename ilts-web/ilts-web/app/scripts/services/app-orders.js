'use strict';

angular.module('lotteryApp')
  .service('AppOrders', function AppOrders($http, ENV, User) {

    var self = this;

    self.orders = [];
    self.customBets = [];
    self.customBetsReady = false;

    self.getOrders = function() {
      return self.orders;
    }

    self.refreshAllOrders = function() {
      var url = ENV.apiEndpoint + '/cart/getAllOrders';
      $http({
        url: url,
        method: 'GET',
        params: {
          "token": User.getToken()
        }
      })
        .success(function(data, status, headers, config){
          self.orders = data
        });
    }

    self.refreshAllOrders();

    self.getCustomBets = function() {
      return self.customBets;
    }

    self.retrieveCustomBets = function(data) {
      var url = ENV.apiEndpoint + '/cart/getAllOrders';
      $http({
        url: url,
        method: 'GET',
        params: {
          "token": User.getToken()
        }
      })
        .success(function(data, status, headers, config){
          self.customBets = data
        });
    }

    self.betReportIsReady = function() {
      return self.customBetsReady;
    }

  });
