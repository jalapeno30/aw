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
    //username not saving to local storage, neet to debug
    self.username = User.username;
    self.orders;
    self.cost;
    self.status = 0;
//    transactionId should be coming from step0 (step0Back)
    self.transactionId = 12345;
//    should be coming from form
    self.cardDetails = function (save){ 
        return{
        cardDetailsUuid: "",
        cardFirstname: "first",
        cardLastname: "last",
        cardType: "visa",
        cardNumber: "100 000 000 000",
        cardCvvNumber: 123,
        cardStreetaddress: "1 street",
        cardAptBdg: "",
        cardCity: "city",
        cardState: "state",
        cardCountry: "country",
        cardZipcode: 99999,
        cardPhoneNumber:12345,
        cardEmailId: 1,
        cardSave: save
        };
    };

    Orders.refreshOrders();
    $scope.$watch(function(){
      return Orders.getOrders();
    }, function(n, o){
      self.orders = n;
      self.cost = Orders.getTotalAmount();
    }, true);

    self.step0Out;
    self.step0Back;
    self.step1Out;
    self.step1Back;
    self.step2Out;
    self.step2Back;
    self.step3Out;
    self.step3Back;    

    self.step0 = function (){ 
        Payment.newCheckout(self.token, self.username, self.orders, self.status).then(function(response){
            self.step0Out = Payment.step0Out;
            self.step0Back = response.data;
            return response.data;
        });        
    };

    self.step1 = function (){
        var status = 1      
        Payment.confirmOrders(self.token, self.username, self.transactionId, status).then(function(response){
            self.step1Out = Payment.step1Out;
            self.step1Back = response.data;
            return response.data;
        });        
  };

    self.step2 = function(){
        var status = 2;
        Payment.verifyCardDetails(self.token, self.username, self.transactionId, status, self.cardDetails(true)).then(function(response){
            self.step2Out = Payment.step2Out;
            self.step2Back = response.data;
            return response.data;
        });     
    };

    self.step3 = function(){
        var status = 3;
        Payment.processTransaction(self.token, self.username, self.transactionId, status, self.cardDetails(true)).then(function(response){
            self.step3Out = Payment.step3Out;
            self.step3Back = response.data;
            return response.data;
        });     
    };

    self.getCards = function(){
        Payment.getCards(self.token, self.username, self.cardDetails(true)).then(function(response){
            self.getCardsOut = Payment.getCardsOut;
            self.getCardsBack = response.data;
            return response.data;
        });     
    };

    self.addCard = function(){
        Payment.editCards(self.token, self.username, self.cardDetails(true)).then(function(response){
            self.editCardsOut = Payment.editCardsOut;
            self.editCardsBack = response.data;
            return response.data;
        });     
    };

    self.deleteCard = function(){
        Payment.editCards(self.token, self.username, self.cardDetails(false)).then(function(response){
            self.editCardsOut = Payment.editCardsOut;
            self.editCardsBack = response.data;
            return response.data;
        });     
    };

});