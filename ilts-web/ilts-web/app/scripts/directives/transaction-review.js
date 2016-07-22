'use strict';

angular
  .module('lotteryApp')
  .directive('transactionReview', transactionReview);

function transactionReview() {
  var directive = {
    restrict: 'E',
    controller: transactionReviewController,
    controllerAs: 'transactionReview',
    link: transactionReview,
    templateUrl: 'views/directives/transaction-review.html'
  };

  return directive;

  function transactionReviewController() {
    var vm = this;
  }

  function transactionReviewLink(scope, elem, attr, ctrl) {

  }
}