/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: services\user.ja
 */

'use strict';

angular.module('lotteryApp')
.controller('RegisterCtrl', function ($scope, User) {
    $scope.open = function($event) {
    	$event.preventDefault();
    	$event.stopPropagation();

    	$scope.opened = true;
    };

    $scope.user = {
    	"user_name": "",
    	"password": "",
    	"first_name": "",
    	"last_name": "",
    	"birth_date": "",
    	"gender": "",
        "email_id": "",
        "phone_number": "",
        "role_name": "User",
        "account_number": "",
        "address_1": "",
        "address_2": "",
        "city": "",
        "state": "",
        "zipcode": "",
        "country": ""
    };

    $scope.register = function() {
        if (document.getElementById("register_gender1").checked === true)
            $scope.user.gender = "male";
        if (document.getElementById("register_gender2").checked === true)
            $scope.user.gender = "female";

    	User.register($scope.user);
    };

    $scope.clearEntries = function() {
        $scope.user.user_name = "";
    	$scope.user.password = "";
        $scope.user.first_name = "";
    	$scope.user.last_name = "";
        $scope.user.birth_date = "";
    	$scope.user.gender = "";
        $scope.user.email_id = "";
    	$scope.user.phone_number = "";
    	$scope.user.role_name = "User";
        $scope.user.account_number = "";
        $scope.user.address_1 = "";
        $scope.user.address_2 = "";
        $scope.user.city = "";
        $scope.user.state = "";
        $scope.user.zipcode = "";
        $scope.user.country = "";
    };

    $scope.getEntryErrorStatus = function(name) {
        if (name === "user_name") {
            if (registerForm.register_user_name.attributes.errmsg.nodeValue === "true")
                return true;
            else
                return false;
        }
        if (name === "email") {
            if (registerForm.register_email_id.attributes.errmsg.nodeValue === "true")
                return true;
            else
                return false;
        }
    };

    $scope.resetEntryErrorStatus = function(name) {
        if (name === "user_name") {
            registerForm.register_user_name.attributes.errmsg.nodeValue = "false";
            var obj = document.getElementById("alert_register_" + name);
            obj.innerHTML = "";
        }
        if (name === "email") {
            registerForm.register_email_id.attributes.errmsg.nodeValue = "false";
            var obj = document.getElementById("alert_register_" + name);
            obj.innerHTML = "";
        }
    };
});

angular.module('lotteryApp')
.directive('errmsg', function (){ 
    return {
        require: 'ngModel',
        link: function(scope, elem, attr, ngModel) {
            var errmsg_value = attr.errmsg;

            ngModel.$parsers.unshift(function (value) {
                console.log(value);

                ngModel.$setValidity('errmsg', new Object());

                return value;
            });

//            scope.$watch(attr.errmsg, function(v) { 
//            });
      }
    };
});
