'use strict';

angular.module('lotteryApp')
  .controller('AppPurchasesCtrl', function ($scope, AppPurchases, $filter, ngTableParams) {

    var self = this;
    self.purchases = AppPurchases.getPurchases();

    $scope.$watch(function(){
      return AppPurchases.getPurchases();
    }, function(newVal){
      self.purchases = newVal;
      self.tableParams.reload();
    });

    self.tableParams = new ngTableParams({
      page : 1,
      count : 10,
      sorting: {
        id: 'asc'
      }
    }, {
      total : 0,
      getData : function($defer, params) {
        var orderedData = params.sorting() ? $filter('orderBy')(self.purchases, params.orderBy()) : self.purchases;
        params.total(self.purchases.length);
        $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
      }
    });

    AppPurchases.retrieveAllPurchases();

  });
