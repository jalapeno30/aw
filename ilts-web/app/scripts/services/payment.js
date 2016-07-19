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

    //step 0 this sends the token, id, and initial status
    self.newCheckout = function(token, userId, status){
        var url = ENV.apiEndpoint + '/payment/newCheckout';
        
        var data = {
          token: token,
          userId: userId,
          status: status
        };
        
        return $http.post(url, data);
    };
};
