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
    var date = new Date();
    var self = this;
    self.step0Out;
    self.step1Out;
    self.step2AOut;
    self.step2BOut;
    self.step3Out;
    self.existingCardOut;
    self.deleteCardOut;

    //step 0 this sends the token and userID, no status needed
    self.purchaseBet = function(token, userId, orderIds){
        var url = ENV.apiEndpoint + '/payment/purchaseBet';        
        var data = {
          token: token,
          userId: userId,
          orderIds: orderIds,
          date: date
        };
        //this is temp
        self.step0Out = url.concat(JSON.stringify(data, null, ' '));    
        //this should return a transactionId
        return $http.post(url, data).then(function(response){
            return response;
        }, function(response){  
            return response;            
        });  
    };  
    
    //Step 1 doesnt need to do anything dor now
//    self.confirmOrders = function(token, userId, transactionId, status){
//        var url = ENV.apiEndpoint + '/payment/confirmOrders';       
//        var data = {
//          token: token,
//          userId: userId,
//          transactionId: transactionId,
//          status: status
//        };
//        
//        //this is temp
//        self.step1Out = url.concat(JSON.stringify(data));   
//        //this returns a success, tells us orders are confirmed by user
//        return $http.post(url, data).then(function(response){
//            return response;
//        }, function(response){     
//            return response;            
//        });  
//    };
    
    //Step 2A send new card data
    self.newCardDetails = function(token, jsonCard, transId, userId){
        var url = ENV.apiEndpoint + '/payment/newCardDetails';
        jsonCard.token = token;
        jsonCard.transId = transId;
        jsonCard.userId = userId;
        
        var data = jsonCard;
        //this is temp
        self.step2AOut = url.concat(JSON.stringify(data, null, ' ')); 
        //this returns a success, tells us card is good
        return $http.post(url, data).then(function(response){
            return response;
        }, function(response){     
            return response;            
        });  
    };
    
    //Step 2b send existing card data
    self.continueExistingCard = function(token, cardId, transId, userId){
        var url = ENV.apiEndpoint + '/payment/continueExistingCard';     
        var data = {
            token: token,
            cardId: cardId,
            transId: transId,
            userId: userId
        };
        //this is temp
        self.step2BOut = url.concat(JSON.stringify(data, null, ' '));
        //this returns a success, tells us card is good
        return $http.post(url, data).then(function(response){
            return response;
        }, function(response){     
            return response;            
        });  
    };    
    
    //Step 3 final confirmations only status update needed
    self.updateStatus = function(token, userId, transId, status){
        var url = ENV.apiEndpoint + '/payment/status';   
        var data = {
            token: token,
            userId: userId,
            transactionId: transId,
            status: status
        };
        //this is temp
        self.step3Out = url.concat(JSON.stringify(data, null, ' '));
        //this returns a success, tells us everything has worked and status us updated
        return $http.post(url, data).then(function(response){
            return response;
        }, function(response){     
            return response;            
        });  
    };
    
    //get cards based on user
    self.existingCardDetails = function(token, userId){
        var url = ENV.apiEndpoint + '/payment/existingCardDetails';         
        var data = {
            token: token,
            userId: userId,
        };
        //this is temp
        self.existingCardOut = url.concat(JSON.stringify(data, null, ' '));
        //this returns a success
        return $http.post(url, data).then(function(response){
            return response;
        }, function(response){     
            return response;            
        });  
    };
    
    //delete cards 
    self.deleteCard = function(token, userId, cardId){
        var url = ENV.apiEndpoint + '/payment/deleteCard';   
        var data = {
          token: token,
          userId: userId,
          cardId: cardId
        };
        //this is temp
        self.deleteCardOut = url.concat(JSON.stringify(data, null, ' '));
        //this returns a success
        return $http.post(url, data).then(function(response){
            return response;
        }, function(response){     
            return response;            
        });  
    };
   
};
