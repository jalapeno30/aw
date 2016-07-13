'use strict';

angular.module('lotteryApp')
  .controller('LogTransactionCtrl', function ($scope, Logs, ngTableParams, $filter) {

        var self = this;
        Logs.refreshTransactionLog();
        self.transactions = Logs.getLog("transaction");

        $scope.$watch(function(){
            return Logs.getLog("transaction");
        }, function(newVal){
            self.transactions = newVal;
            self.tableParams.reload();
        });

        self.tableParams = new ngTableParams({
            page: 1,
            count: 10,
            sorting: {
                id: 'asc'
            }
        }, {
            total: 0,
            getData: function($defer, params) {
                var orderedData = params.sorting() ? $filter('orderBy')(self.transactions, params.orderBy()) : self.transactions;
                params.total(self.transactions.length);
                $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
            }
        });

  });
