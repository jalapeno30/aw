'use strict';

angular
  .module('lotteryApp')
  .directive('paymentSuccess', paymentSuccess);

function paymentSuccess() {
  var directive = {
    restrict: 'E',
    controller: paymentSuccessController,
    controllerAs: 'paymentSuccess',
    templateUrl: 'views/directives/payment-success.html'
  };

  return directive;

  function paymentSuccessController(Payment, Orders, $scope) {
    var self = this;
    self.name;
    //transId will come from payment-screen.js
    self.d = new Date().toLocaleDateString();
    self.o = Orders.getTotalAmount();
    
    $scope.$watch(function() { return Payment.cardReview}, function(newVal) { 
        self.name = newVal; 
   }, true);
  }
}