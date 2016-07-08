'use strict';

angular.module('lotteryApp')
  .controller('MainCtrl', function ($scope, Main, $http, $translate) {

  	angular.element(document).ready(function () {
        FastClick.attach(document.body);
    });

  	$scope.alerts = [];

    $scope.$watch(function(){ return Main.getHeading(); }, function(heading){
      $scope.heading = heading;
    }, true);

    $scope.addAlert = function(message, type) {
    	if (message != undefined) {
    		if (type == undefined) {
	    		$scope.alerts.push({msg: message});
	    	} else {
	    		$scope.alerts.push({msg: message, type: type});	
	    	}
	    }
  	};

  	$scope.closeAlert = function(index) {
    	$scope.alerts.splice(index, 1);
  	};	

  	$scope.clearAlerts = function() {
  		$scope.alerts = [];
  	}

  });
