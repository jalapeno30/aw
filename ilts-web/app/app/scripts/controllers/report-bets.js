'use strict';

angular.module('lotteryApp')
  .controller('ReportBetsCtrl', function ($scope, $filter, AppOrders, ngTableParams, Users) {

    var self = this;

    self.showFilters = false;
    self.selectedFilter = "";
    // self.paymentReportReady = AppPurchases.purchaseReportIsReady();
    self.betReportReady = true;//AppOrders.betReportIsReady();

    self.toggleFilters = function() {
      self.showFilters = !self.showFilters;
    }

    self.filters = [
      {
        id : "user",
        name : "User"
      },
      {
        id : "date",
        name : "Date Range"
      },
      {
        id : "amount",
        name : "Amount",
      },
      {
        id : "status",
        name : "Status"
      }
    ];

    self.statuses = [
      'created',
      'cancelled',
      'completed'
    ];

    self.users = Users.getUsers();

    $scope.$watch(function(){
      return Users.getUsers();
    }, function(newVal){
      self.users = newVal;
    });

    // $scope.$watch(function(){
    //   return AppOrders.betReportIsReady();
    // }, function(newVal){
    //   self.betReportReady = newVal;
    // });

    // default values for form
    self.defaultFiltersForm = {
      "user_selected" : false,
      "user_checked" : false,
      "date_selected" : false,
      "date_checked" : false,
      "amount_selected" : false,
      "amount_checked" : false,
      "status_selected" : false,
      "status_checked" : false
    };

    //object container for dateRange
    self.dateRange = {};

    // convert dateRange object to form parameters
    $scope.$watch(function(){
      return self.dateRange;
    }, function(newVal){
      if (Object.keys(newVal).length > 0) {
        self.defaultFiltersForm.date_values_start = newVal.startDate.format('L').toString();
        self.defaultFiltersForm.date_values_end = newVal.endDate.format('L').toString();
      }
    });

    // set form to default values
    self.betFiltersForm = self.defaultFiltersForm;

    // set form values from url parameters
    // if ($location.search() != {}) {
    //   self.paymentFiltersForm = self.defaultFiltersForm;
    //   var params = $location.search();
    //   for(var key in params) {
    //     if (key != 'date_values_end' && key != 'date_values_start') {
    //       self.paymentFiltersForm[key] = params[key];
    //     }
    //   }
    //   // set date url parameters and convert to dateRange objecta
    //   if (typeof params['date_values_end'] !== 'undefined') {
    //     self.dateRange = {
    //       "startDate" : moment(params['date_values_start']),
    //       "endDate" : moment(params['date_values_end'])
    //     };
    //   }
    //   self.showFilters = true;
    // }
    //
    self.selectFilter = function() {

      if (self.selectedFilter != "") {
        self.betFiltersForm[self.selectedFilter + "_selected"] = true; // show
        self.betFiltersForm[self.selectedFilter + "_checked"] = true;  //check
      }

    }

    self.isSelectedFilter = function(filter) {
      return self.betFiltersForm[filter + "_selected"] === true || self.betFiltersForm[filter + "_selected"] === 'true';
    }

    // self.generateBetReport = function() {
    //   $location.search(self.betFiltersForm);
    //   $route.reload();
    // }

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
