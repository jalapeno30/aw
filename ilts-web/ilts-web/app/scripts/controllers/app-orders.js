'use strict';

angular.module('lotteryApp')
  .controller('AppOrdersCtrl', function ($scope, $filter, AppOrders, ngTableParams) {

    var self = this;
    self.orders = AppOrders.getOrders();

    $scope.$watch(function(){
      return AppOrders.getOrders();
    }, function(newVal){
      self.orders = newVal;
      self.tableParams.reload();
    });

    self.refreshOrders = function() {
      AppOrders.refreshAllOrders();
    }

    self.tableParams = new ngTableParams({
      page: 1,
      count: 10,
      sorting: {
        id: 'asc'
      }
    }, {
      total: 0,
      getData: function($defer, params) {
        var orderedData = params.sorting() ? $filter('orderBy')(self.orders, params.orderBy()) : self.orders;
        params.total(self.orders.length);
        $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
      }
    });

  });
