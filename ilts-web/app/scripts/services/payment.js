/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 * 
 * File: services\payment.js
 */
'use strict';

angular
    .module('lotteryApp')
    .service('Payment', Payment);

function Payment($http, ENV) {
    var self = this;
    //this will store the card info for the final review stage of the wizard
    self.cardReview;
    //all of the rest are temp
    self.step0Out;
    self.step1Out;
    self.step2AOut;
    self.step2BOut;
    self.step3Out;
    self.existingCardOut;
    self.deleteCardOut;

    //step 0 TEMPORARYthis sends the token and userID, no status needed. simulates buy now from shopping cart
    self.purchaseBetT = function(token, userId, orderIds){
        var url = ENV.apiEndpoint + '/betting/purchaseBet?token=' + token;   
        var date = new Date();
        var options = {
            weekday: "long", year: "numeric", month: "short",
            day: "numeric", hour: "2-digit", minute: "2-digit"
        };
        var data = {
          userId: userId,
          orderIds: orderIds,
          date: date.toLocaleTimeString("en-us", options)
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
    //final version of above
    self.purchaseBet = function(token, userId, orderIds){
        var url = ENV.apiEndpoint + '/betting/purchaseBet?token=' + token;    
        var date = new Date();
        var options = {
            weekday: "long", year: "numeric", month: "short",
            day: "numeric", hour: "2-digit", minute: "2-digit"
        };
        var data = {
          userId: userId,
          orderIds: orderIds,
          date: date.toLocaleTimeString("en-us", options)
        };
        //this should return a transactionId or send an error. TransId assigned back to service from controller
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
    
//    //Step 2A send new card data
//    self.newCardDetails = function(token, jsonCard, transId, userId){
//        var url = ENV.apiEndpoint + '/payment/newCardDetails?token=' + token;         
//        var data = {
//          jsonCard: jsonCard,
//          transId: transId,
//          userId: userId,
//       
//        }; 
//        //this is temp
//        self.step2AOut = url.concat(JSON.stringify(data, null, ' ')); 
//        //this returns a success, tells us card is good
//        return $http.post(url, data).then(function(response){
//            return response;
//        }, function(response){     
//            return response;            
//        });  
//    };

 //Step 2A send new card data simulates clicking continue from new card screen
    self.newCardDetailsT = function(token, jsonCard, transId, userId){
        var url = ENV.apiEndpoint + '/betting/newCardDetails?token='+token;
        jsonCard.userId = userId;
        jsonCard.transId = transId;
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
    
    //Final version of above
    self.newCardDetails = function(token, jsonCard, transId, userId){
        var url = ENV.apiEndpoint + '/betting/newCardDetails?token='+token;
        jsonCard.userId = userId;
        jsonCard.transId = transId;
        var data = jsonCard;
        //this returns a success, tells us card is good
        return $http.post(url, data).then(function(response){
            return response;
        }, function(response){     
            return response;            
        });  
    };
    
    
    //Step 2b send existing card data simulates clicking continue after selecting existing card
    self.continueExistingCardT = function(token, cardId, transId, userId){
        var url = ENV.apiEndpoint + '/betting/getSelectedCard?token=' + token;     
        var data = {
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
    
    //perm version of above
    self.continueExistingCard = function(token, cardNumber, transId, userId){
        var url = ENV.apiEndpoint + '/betting/getSelectedCard?token=' + token;    
        var data = {
          cardNumber: cardNumber,
          transId: transId,
          userId: userId
        };
        //this returns a success, tells us card if card is good
        return $http.post(url, data).then(function(response){
            return response;
        }, function(response){     
            return response;            
        });  
    };    
    
    //Step 3 final confirmations only status update needed. This simulates any status update needed 
    self.updateStatusT = function(token, userId, transId, status){
        var url = ENV.apiEndpoint + '/betting/status?token=' + token;   
        var data = {
          userId: userId,
          transId: transId,
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
    
    //final
    self.updateStatus = function(token, userId, transId, status){
        var url = ENV.apiEndpoint + '/betting/status?token=' + token;   
        var data = {
          userId: userId,
          transId: transId,
          status: status
        };
        //this returns a success, tells us everything has worked and status us updated
        return $http.post(url, data).then(function(response){
            return response;
        }, function(response){     
            return response;            
        });  
    };
    
    //get cards based on user. This sikulates clicking button to get users existing card
    self.existingCardDetailsT = function(token, userId){
        var url = ENV.apiEndpoint + '/betting/sendExistingCardDetails?token=' + token + '&userId=' +userId;         
        var data = {
          userId: userId
        };
        //this is temp
        self.existingCardOut = url.concat(JSON.stringify(data, null, ' '));
        //this returns a success
        return $http.post(url).then(function(response){
            return response;
        }, function(response){     
            return response;            
        });  
    };
    
    //final version of above
    self.existingCardDetails = function(token, userId){
        var url = ENV.apiEndpoint + '/betting/sendExistingCardDetails?token=' + token + '&userId=' +userId;                  
        //this returns a success
        return $http.post(url).then(function(response){
            return response;
        }, function(response){     
            return response;            
        });  
    };
    
    //delete card for a user
    self.deleteCard = function(token, userId, cardId){
        var url = ENV.apiEndpoint + '/betting/deleteCard?token=' + token;   
        var data = {
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
    
    self.setCard = function(cardSelection, cardData){
        self.cardReview = cardData;
    };
       
};
