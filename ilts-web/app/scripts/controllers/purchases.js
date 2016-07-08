'use strict';

angular.module('lotteryApp')
  .controller('PurchasesCtrl', function ($scope, Orders, User, $timeout) {
  	$scope.heading = 'Purchases';
  	$scope.showDetails = [];
    $scope.checkouts = [];

    $scope.$watchCollection(function(){
        return [User.logged, User.name];
      }, function(newvals){
          if (newvals[0] != undefined && newvals[0] != true && newvals[0] != "true") {
            window.location = "/#";
          }
      });

    refreshPurchases();
    function refreshPurchases() {
      Orders.retrieveCheckouts();
      $scope.UTIL.setTimeout($scope, refreshPurchases, 5000);
    }

    $scope.$watch(function(){
      return Orders.getCheckouts();
    }, function(data){
      $scope.checkouts = data;
      // store showDetails array
      var temp = $scope.showDetails;

      $scope.showDetails = [];

      for (var i = 0; i < $scope.checkouts.length; i++) {
        var id = $scope.checkouts[i].id;

        if (temp[id] === undefined) {
          $scope.showDetails[id] = false;
        } else {
          $scope.showDetails[id] = temp[id];
        }
      }


      // if ($scope.checkouts.length > $scope.showDetails.length) {
      // 	var f = $scope.checkouts.length;
      // 	var e = $scope.showDetails.length;
      // 	for (var i = 0; i < f - e; i++) {
      // 		$scope.showDetails.push(false);
      // 	}
      //   console.log($scope.checkouts);
    //   }
    });



    // $scope.clearHistory = function() {
    // 	Orders.emptyCheckouts();
    // }

    // $scope.toggleCheckoutDetails = function(index) {
    // 	$scope.showDetails[index] = !$scope.showDetails[index];
    // 	// console.log($scope.checkouts[index]);
    // }

    $scope.toggleCheckoutDetails = function(checkoutId) {
      $scope.showDetails[checkoutId] = !$scope.showDetails[checkoutId];

    }

  });
