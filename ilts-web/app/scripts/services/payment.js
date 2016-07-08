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
    var payments = [];

    function fetchPayments() {
        return $http({
            url: ENV.apiEndpoint + '/paymentstuff',
            method: 'GET'
        }).then(function(result) {
            payments = result.data;
        });
    }

    function getPayments() {
        return payments;
    }

    return {
        fetchPayments: fetchPayments,
        getPayments: getPayments,
    }
}
