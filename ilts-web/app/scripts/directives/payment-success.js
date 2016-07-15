'use strict';

angular
  .module('lotteryApp')
  .directive('paymentSuccess', paymentSuccess);

function paymentSuccess() {
  var directive = {
    restrict: 'E',
    controller: paymentSuccessController,
    controllerAs: 'paymentSuccess',
    link: paymentSuccess,
    templateUrl: 'views/directives/payment-success.html'
  };

  return directive;

  function paymentSuccessController() {
    var vm = this;
  }

  function paymentSuccessLink(scope, elem, attr, ctrl) {

  }
}