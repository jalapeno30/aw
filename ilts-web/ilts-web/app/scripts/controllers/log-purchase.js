'use strict';

angular.module('lotteryApp')
  .controller('LogPurchaseCtrl', function ($scope, Logs, ngTableParams, $filter) {

        var self = this;
        Logs.refreshPurchaseLog();
        self.purchases = Logs.getLog("purchase");

        $scope.$watch(function(){
            return Logs.getLog("purchase");
        }, function(newVal){
            self.purchases = newVal;
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
                var orderedData = params.sorting() ? $filter('orderBy')(self.purchases, params.orderBy()) : self.purchases;
                params.total(self.purchases.length);
                $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
            }
        });

  });
