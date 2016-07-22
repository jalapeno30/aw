'use strict';

angular.module('lotteryApp')
  .controller('PaypalCallbackCtrl', function ($scope, $location, $window, $rootScope) {

  	$scope.params = $location.search();
  	$scope.keys = ['scope', 'code'];

  	$scope.error = false;
  	$scope.hide = true;



    $scope.checkForParams = function() {

    	if (top.window.opener === null) {
	  		window.location = '/';
	  	} else {

	    	if ($scope.params === {}) {

	    		window.location = '/';
	    	} else {
	    		for (var i = 0; i < $scope.keys.length; i++) {

	    			if ($scope.params[$scope.keys[i]] === undefined) {

	    				$scope.error = true;
	    				break;
	    			}
	    		}
	    		$scope.hide = false;

	    		if ($scope.error === false) {
	    			var orig_location = top.window.opener.location;
	    			var data = $scope.params;
	    			data.location = orig_location;
	    			top.window.opener.location = 'http://127.0.0.1:9000/#/paypal-action?' + $scope.UTIL.encodeQueryData(data);
	    			window.close();
	    		}
	    	}
	    }
    }

    $scope.checkForParams();

  });
