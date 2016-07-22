'use strict';

angular.module('lotteryApp')
  .service('Bets', function Bets(User, ENV, $http) {

        var self = this;

        var pendingBets = [];
        var cost = {
            cost: 0.0,
            tax: 0.0,
            total: 0.0
        };

        self.getPendingBets = function(){
            return pendingBets;
        };

        self.getCost = function() {
            return cost;
        }

        self.retrievePendingBets = function() {
            var url = ENV.apiEndpoint + "/betting/pending";
            $http({
                url: url,
                method: 'GET',
                params: {
                    token: User.getToken()
                }
            })
                .success(function(data){
                    pendingBets = data;
                })
                .error(function(data){
                    console.log(data);
                });
        };

        self.retrievePendingBetsCost = function() {
            var url = ENV.apiEndpoint + "/betting/pending/cost";
            $http({
                url: url,
                method: 'GET',
                params: {
                    token: User.getToken()
                }
            })
                .success(function(data){
                    cost.cost = data.cost;
                    cost.tax = data.tax;
                    cost.total = data.total;
                })
                .error(function(data){
                    console.log(data);
                });
        }

  });
