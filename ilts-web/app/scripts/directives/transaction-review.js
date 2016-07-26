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
    var self = this;
    
    self.dummyOrder ={
        one:   {draw: '07-20-16',number: "1-5-7-9", cost: 10},
        two:   {draw: '07-20-16', number: "2-4-6-8", cost: 10},
        three: {draw: '07-20-16', number: "3-10-12", cost: 10}        
    };
    
    self.dummyTotal = "$30.00"
    
    self.dummyCard = {name: "Bob Jones", cardNumber: "1234-4567-8546", address: "13 Apple St, Vista CA"};
  
  }

  function transactionReviewLink(scope, elem, attr, ctrl) {

  }
}