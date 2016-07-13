'use strict';

angular.module('lotteryApp')
  .controller('PaypalFundConfirmCtrl', function ($scope, $location, $routeParams, ENV, $http, blockUI) {
    $scope.params = $routeParams;

    var url = ENV.apiEndpoint + '/betting/paypal/confirm';

    blockUI.start();
  	$http.get(url, {params: $scope.params})
  		.success(function(data){
  			window.location = "/#/play";
  			 blockUI.stop();
  		});
  });
