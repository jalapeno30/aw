'use strict';

angular.module('lotteryApp')
  .controller('UserProfileCtrl', function ($scope, Paypal) {

  	$scope.paypal = {
  		loggedIn: false
  	}

  	$scope.$watch(function(){
  		return Paypal.getAccessToken();
  	}, function(newVal, oldVal){

  		if (newVal === '') {
  			$scope.paypal.loggedIn = false;
  		} else {
  			$scope.paypal.loggedIn = true;
  		}
  	});


  });
