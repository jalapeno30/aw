/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * it3
 */


'use strict';

angular.module('lotteryApp')
  .controller('PaymentTestCtrl', function (User, Orders, $scope, Payment) {
      
    var self=this;
    self.token = User.getToken();
    self.userId = User.id;
    self.orderIds;
    self.cost;
    self.status = 0;
//    transactionId should be coming from step0 (step0Back)
    self.transId;
//    should be coming from form
    self.jsonCard = function(save) { 
        return{
        "cardFirstname": "first",
        "cardLastname": "last",
        "cardType": "visa",
        "cardNumber": "100 000 000 000",
        "cardCvvNumber": 123,
        "cardStreetaddress": "1 street",
        "cardAptBdg": "",
        "cardCity": "city",
        "cardState": "state",
        "cardCountry": "country",
        "cardZipcode": 99999,
        "cardPhoneNumber":12345,
        "cardEmailId": "email",
        "cardSave": save
        };
    };
//    should be coming from 2b back
    self.cardId = 1;
//    temporary
    self.step0Out;
    self.step0Back;
    self.step1Out;
    self.step1Back;
    self.step2AOut;
    self.step2ABack;
    self.step2BOut;
    self.step2BBack;
    self.step3Out;
    self.step3Back;
    self.existingCardOut;
    self.existingCardBack; 
    self.deleteCardOut;
    self.deleteCardBack;
   
    Orders.refreshOrders();
    $scope.$watch(function(){
      return Orders.getOrders();
    }, function(){
      self.orderIds = Orders.getOrderIds();
    }, true);
    
    //this would be clicking buy now in the original shopping cart
    self.step0 = function (){ 
        Payment.purchaseBet(self.token, self.userId, self.orderIds).then(function(response){
            self.step0Out = Payment.step0Out;
            //should return transId
            self.step0Back = JSON.stringify(response.data, null, ' ');
            self.transId = response.data.transId;
        });        
    };

//    NO LONGER NEEDED (nOT UPDATING STATUS)
//    self.step1 = function (){
//        var status = 1      
//        Payment.confirmOrders(self.token, self.username, self.transactionId, status).then(function(response){
//            self.step1Out = Payment.step1Out;
//            self.step1Back = response.data;
//            return response.data;
//        });        
//  };

    //this would be sending new card data
    self.step2A = function(){
        Payment.newCardDetails(self.token, self.jsonCard(true), self.transId, self.userId).then(function(response){
            self.step2AOut = Payment.step2AOut;
            self.step2ABack = response.data;
            return response.data;
        });     
    };
    
    //this would be sending existing card data
    self.step2B = function(){
        Payment.continueExistingCard(self.token, self.cardId, self.transId, self.userId).then(function(response){
            self.step2BOut = Payment.step2BOut;
            self.step2BBack = response.data;
            return response.data;
        });     
    };

    //This is for both the Confrim & Buy now steps. Shared because the only thing that happens is a status update
    self.step3 = function(status){
        var status = status;
        Payment.updateStatus(self.token, self.userId, self.transId, status).then(function(response){
            self.step3Out = Payment.step3Out;
            self.step3Back = response.data;
            return response.data;
        });     
    };

    //This would be when the user clicks existing card button
    self.existingCardDetails = function(){
        Payment.existingCardDetails(self.token, self.userId).then(function(response){
            self.existingCardOut = Payment.existingCardOut;
            //should return cardIds
            self.existingCardBack = response.data;
            return response.data;
        });     
    };

    self.deleteCard = function(){
        Payment.deleteCard(self.token, self.userId, self.cardId).then(function(response){
            self.deleteCardOut = Payment.deleteCardOut;
            self.deleteCardBack = response.data;
            return response.data;
        });     
    };

});