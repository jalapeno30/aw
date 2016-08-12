'use strict';

angular
  .module('lotteryApp')
  .directive('transactionReview', transactionReview);

function transactionReview(Payment) {
  var directive = {
    restrict: 'E',
    controller: transactionReviewController,
    controllerAs: 'transactionReview',
    templateUrl: 'views/directives/transaction-review.html'
  };

  return directive;

  function transactionReviewController($scope, Orders) {
    var self = this;
    self.cardReview;
    self.orders = Orders.getOrders();
    self.total = Orders.getTotalAmount();
    
    $scope.$watch(function() { return Payment.cardReview}, function(newVal) { 
        self.cardReview = newVal; 
   }, true);
  
  }
}