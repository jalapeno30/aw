'use strict';

angular.module('lotteryApp')
  .controller('ReportTicketsCtrl', function ($scope, AppPurchases, $filter, Users, ngTableParams, $location, $route) {

    var self = this;

    self.showFilters = false;
    self.selectedFilter = "";
    self.paymentReportReady = AppPurchases.purchaseReportIsReady();

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

    $scope.$watch(function(){
      return AppPurchases.purchaseReportIsReady();
    }, function(newVal){
      self.paymentReportReady = newVal;
    });

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
    self.paymentFiltersForm = self.defaultFiltersForm;

    // set form values from url parameters
    if ($location.search() != {}) {
      self.paymentFiltersForm = self.defaultFiltersForm;
      var params = $location.search();
      for(var key in params) {
        if (key != 'date_values_end' && key != 'date_values_start') {
          self.paymentFiltersForm[key] = params[key];
        }
      }
      // set date url parameters and convert to dateRange objecta
      if (typeof params['date_values_end'] !== 'undefined') {
        self.dateRange = {
          "startDate" : moment(params['date_values_start']),
          "endDate" : moment(params['date_values_end'])
        };
      }
      self.showFilters = true;
    }

    self.selectFilter = function() {

      if (self.selectedFilter != "") {
        self.paymentFiltersForm[self.selectedFilter + "_selected"] = true; // show
        self.paymentFiltersForm[self.selectedFilter + "_checked"] = true;  //check
      }

    }

    self.isSelectedFilter = function(filter) {
      return self.paymentFiltersForm[filter + "_selected"] === true || self.paymentFiltersForm[filter + "_selected"] === 'true';
    }

    self.generatePaymentReport = function() {
      $location.search(self.paymentFiltersForm);
      $route.reload();
    }

    self.purchases = AppPurchases.getCustomPurchases();

    $scope.$watch(function(){
      return AppPurchases.getCustomPurchases();
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

    AppPurchases.retrieveCustomPurchases($location.search());
    // AppPurchases.retrieveAllPurchases();

  });
