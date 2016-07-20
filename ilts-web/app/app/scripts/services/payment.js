/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 * it3
 * File: services\payment.js
 */
'use strict';

angular
    .module('lotteryApp')
    .service('Payment', Payment);

function Payment($http, ENV) {
    var self = this;
    self.step0Out;
    self.step1Out;
    self.step2Out;
    self.step3Out;
    self.getCardsOut;
    self.editCardsOut;

    //step 0 this sends the token, id, and initial status
    self.newCheckout = function(token, userId, orders, status){
        var url = ENV.apiEndpoint + '/payment/newCheckout';        
        var data = {
          token: token,
          userName: userId,
          orders: orders,
          status: status
        };
        //this is temp
        self.step0Out = url.concat(JSON.stringify(data));    
        //this should return a transactionId
        return $http.post(url, data).then(function(response){
            return response;
        }, function(response){  
            return response;            
        });  
    };
    
    //Step 1 just updates status for now
    self.confirmOrders = function(token, userId, transactionId, status){
        var url = ENV.apiEndpoint + '/payment/confirmOrders';       
        var data = {
          token: token,
          userId: userId,
          transactionId: transactionId,
          status: status
        };
        
        //this is temp
        self.step1Out = url.concat(JSON.stringify(data));   
        //this returns a success, tells us orders are confirmed by user
        return $http.post(url, data).then(function(response){
            return response;
        }, function(response){     
            return response;            
        });  
    };
    
    //Step 2 verify card data
    self.verifyCardDetails = function(token, userId, transactionId, status, cardDetails){
        var url = ENV.apiEndpoint + '/payment/sendCardData';        
        var data = {
          token: token,
          userId: userId,
          transactionId: transactionId,
          status: status,
          cardDetails: cardDetails
        };
        //this is temp
        self.step2Out = url.concat(JSON.stringify(data));   
        //this returns a success, tells us card is good
        return $http.post(url, data).then(function(response){
            return response;
        }, function(response){     
            return response;            
        });  
    };
    
    //Step 3 final confirmations
    self.processTransaction = function(token, userId, transactionId, cardDetails, status){
        var url = ENV.apiEndpoint + '/payment/processTransaction';        
        var data = {
          token: token,
          userId: userId,
          transactionId: transactionId,
          cardDetails: cardDetails,
          status: status
        };
        //this is temp
        self.step3Out = url.concat(JSON.stringify(data));   
        //this returns a success, tells us everything has worked and payment has processed 
        return $http.post(url, data).then(function(response){
            return response;
        }, function(response){     
            return response;            
        });  
    };
    
    //get cards based on user
    self.getCards = function(token, userId){
        var url = ENV.apiEndpoint + '/payment/getCards';        
        var config = {
          params:{
              token: token,
              userId: userId
          }
        };
        //this is temp
        self.getCardsOut = url.concat(JSON.stringify(config));   
        //this returns a list of saved cards
        return $http.post(url, config).then(function(response){
            return response;
        }, function(response){     
            return response;            
        });  
    };
    
    //add or delete cards 
    self.editCards = function(token, userId, cardDetails){
        var url = ENV.apiEndpoint + '/payment/editCards';        
        var data = {
          token: token,
          userId: userId,
          cardDetails: cardDetails
        };
        //this is temp
        self.editCardsOut = url.concat(JSON.stringify(data));   
        //this returns a success
        return $http.post(url, data).then(function(response){
            return response;
        }, function(response){     
            return response;            
        });  
    };
   
};
