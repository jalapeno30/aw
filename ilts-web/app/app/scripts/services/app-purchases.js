'use strict';

angular.module('lotteryApp')
  .service('AppPurchases', function AppPurchases($http, ENV, User) {

    var self = this;
    self.purchases = [];
    self.customPurchases = [];
    self.customPurchasesReady = false;

    self.getPurchases = function() {
      return self.purchases;
    };

    self.retrieveAllPurchases = function() {
      var url = ENV.apiEndpoint + '/betting/getAllPurchases';
      $http({
        url: url,
        method: 'GET',
        params: {
          "token" : User.getToken()
        }
      })
        .success(function(data, status, headers, config){
          self.purchases = data;
          for (var i = 0; i < data.length; i++) {
            self.purchases[i].date = new Date(data[i].date.replace(" ", "T"));
          }
        });
    }

    self.retrieveAllPurchases();

    self.getCustomPurchases = function() {
      return self.customPurchases;
    }

    self.retrieveCustomPurchases = function(data) {
      var sendData = data;
      sendData.token = User.getToken();
      var url = ENV.apiEndpoint + '/betting/getPurchasesCustom';
      $http({
        url: url,
        method: 'GET',
        params : sendData
      })
      .success(function(data, status, headers, config){

        self.customPurchases = data;
        for (var i = 0; i < data.length; i++) {
          self.customPurchases[i].date = new Date(data[i].date.replace(" ", "T"));
        }
        self.customPurchasesReady = true;

      });
    }

    self.purchaseReportIsReady = function() {
      return self.customPurchasesReady;
    }

  });
