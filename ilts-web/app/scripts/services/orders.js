/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: services\orders.js
 */
'use strict';

angular.module('lotteryApp')
    .service('Orders', function Orders(localStorageService, $http, ENV, OrderFactory, User, growl) {
    var self = this;
    // var totalAmount = 0;

    var totalAmount = 0;

    // to be sent to server
    var pendingOrders = [];

    // retrieved from server
    var orders = [];
    var checkouts = [];

    for (var i = 0; i < orders.length; i++) {
        totalAmount = parseFloat(totalAmount) + parseFloat(orders[i].orderCost);
    }

    self.getTotalAmount = function() {
        return parseFloat(totalAmount);
    }

    var updateAmount = function() {
        totalAmount = 0;
        for (var i = 0; i < orders.length; i++) {
            totalAmount = parseFloat(totalAmount) + parseFloat(orders[i].orderCost);
        }
    }

    self.addPendingOrder = function(order) {
        pendingOrders.push(order);
    }

    self.sendPendingOrders = function() {
        // push to server
        for (var i = 0; i < pendingOrders.length; i++) {
            var url = ENV.apiEndpoint + '/cart/addOrder';

            var order = pendingOrders[i];
            var orderData = {
                "gameID" : order.getGameID(),
                "drawID" : order.getDrawID(),
                "system" : order.getSystem().systemName,
                "numbers" : order.getSelectedNumbers(),
                "token" : User.getToken()
            }

            $http.post(url, orderData)
                .success(function(data, status, headers, config) {
                    self.refreshOrders();
                    growl.addSuccessMessage('Successfully added to cart.');
                })
                .error(function(data, status, headers, config) {
                    growl.addErrorMessage('Error adding to cart.');
                });
        }
        pendingOrders = [];
    };

    self.refreshOrders = function() {
        var url = ENV.apiEndpoint + '/cart/getOrders';
        $http({
            url: url,
            method: 'GET',
            params: {
                "token": User.getToken()
            }
        })
        .success(function(data, status, headers, config) {
            orders = data;
            updateAmount();
        });
        // orders = [];
        // retrieve from server
    };

    self.getOrders = function() {
        return orders;
    };
    


    self.removeOrder = function(orderID, noGrowl) {
        var url = ENV.apiEndpoint + '/cart/removeOrder/' + orderID;
        $http.delete(url).success(function(data, status, headers, config) {
            // console.log(data);
            self.refreshOrders();
            if(!noGrowl){
                growl.addSuccessMessage('Removed bet from cart.');
            }          
        })
        .error(function(){
            growl.addErrorMessage('Error removing bet from cart.');
        });
    };
    
    self.removeAllOrders = function(orderIds){
        for(var i=0; i<orderIds.length; i++){
            self.removeOrder(orderIds[i], true);
        }
        
    };
    
    self.getOrderIds = function(){        
        var ids = [];
        
        for (var i = 0; i < orders.length; i++) {
            var order = orders[i];
            ids.push(order.id);
        }
        
        return ids;
    };

    self.checkoutOrders = function() {
        var date = new Date();
        var options = {
            weekday: "long", year: "numeric", month: "short",
            day: "numeric", hour: "2-digit", minute: "2-digit"
        };

        var ids = [];

        for (var i = 0; i < orders.length; i++) {
            var order = orders[i];
            ids.push(order.id);
        }

        var checkoutObject = {
            "date" : date.toLocaleTimeString("en-us", options),
            "orders" : ids
        };

        var url = ENV.apiEndpoint + '/betting/purchaseBet?token=' + User.getToken();

        $http.post(url, checkoutObject).then(function(response) {
            window.location = response.data.redirectURI;
        });
    };

    self.retrieveCheckouts = function() {
        var url = ENV.apiEndpoint + '/betting/getPurchases';
        $http({
            url: url,
            method: 'GET',
            params: {
                "token" : User.getToken()
            }
        })
        .success(function(data, status, headers, config) {
            checkouts = data;
            // console.log(data);
        });
    }

    self.getCheckouts = function() {
        // return localStorageService.get('checkouts');
        return checkouts;
    };

    self.emptyCheckouts = function() {
        localStorageService.remove('checkouts');
    };
});
