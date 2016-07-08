'use strict';

angular.module('lotteryApp')
  .controller('PaypalActionCtrl', function ($scope, $location, Paypal) {

  	$scope.params = $location.search();

  	if ($scope.params.location === undefined) {
  		window.location = '/';
  	} else {
  		if ($scope.params.scope !== undefined) {
  			Paypal.setScope($scope.params.scope);
  		}
  		if ($scope.params.code !== undefined) {
  			Paypal.setAccessToken($scope.params.code);
  		}
  		window.location = $scope.params.location;
  	}

  });
