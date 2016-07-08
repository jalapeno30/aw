'use strict';

angular.module('lotteryApp')
  .controller('PaymentsSettingsCtrl', function ($scope, $filter, ngTableParams) {

    var self = this;

    self.paymentOptions = [
      {
        "name" : "Paypal",
        "username" : "ilts",
        "userid" : "123456",
        "status" : "Active"
      },
      {
        "name" : "LoadCentral",
        "username" : "ilts",
        "userid" : "AB-123XX",
        "status" : "Inactive"
      },
      {
        "name" : "Credit Card",
        "username" : "ilts",
        "userid" : "5485-1234-5790",
        "status" : "Inactive"
      }
    ];

    self.tableParams = new ngTableParams({
      page: 1,
      count: 10,
      sorting: {
        name: 'asc'
      }
    }, {
      total: 0,
      getData: function($defer, params) {
        var orderedData = params.sorting() ? $filter('orderBy')(self.paymentOptions, params.orderBy()) : self.paymentOptions;
        params.total(self.paymentOptions.length);
        $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
      }
    });

  });
