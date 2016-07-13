'use strict';

angular.module('lotteryApp')
  .service('Logs', function Logs(User, $http, ENV) {

        var self = this;

        self.data = {
            "transaction" : [],
            "purchase" : [],
            "activePurchase" : [],
            "currentPurchase" : []
        };

        self.getLog = function(logName) {
            return self.data[logName];
        };

        self.setLog = function(logName, data) {
            self.data[logName] = data;
        };

        self.refreshLog = function(logName, method) {
            var url = ENV.apiEndpoint + '/betting/' + method;
            $http({
                url: url,
                method: 'GET',
                params: {
                    "token" : User.getToken()
                }
            })
            .success(function(data, status, headers, config){
                self.setLog(logName, data);
            });
        };

        self.refreshTransactionLog = function() {
            self.refreshLog("transaction", "getTransactionLog");
        };

        self.refreshPurchaseLog = function() {
            self.refreshLog("purchase", "getPurchaseLog");
        };

        self.refreshActivePurchaseLog = function() {
            self.refreshLog("activePurchase", "getActivePurchaseLog");
        };

        self.refreshCurrentPurchaseLog = function() {
            self.refreshLog("currentPurchase", "getCurrentPurchaseLog");
        };

  });
