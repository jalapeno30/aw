'use strict';

angular.module('lotteryApp')
  .controller('ShoppingCartCtrl', function ($scope, Orders, Bets, $modal, $timeout, blockUI) {

  	//$scope.orders = [];
  	//$scope.hasOrders = false;
    //$scope.totalAmount = 0;
    //
    //$scope.$watch(function(){ return Orders.getOrders(); }, function(data){
    //  $scope.orders = data;
    //  $scope.hasOrders = ($scope.orders.length > 0);
    //  $scope.totalAmount = Orders.getTotalAmount();
    //});
    //
    //refreshCart();
    //function refreshCart() {
    //  Orders.refreshOrders();
    //  //$scope.UTIL.setTimeout($scope, refreshCart, 5000);
    //}
    //
    //$scope.removeOrder = function(order) {
    //	Orders.removeOrder(order);
    //  // Orders.refreshOrders();
    //}
    //// $scope.getTotalAmount = function() {
    ////   return Orders.getTotalAmount();
    //// }
    //
    //$scope.checkout = function() {
    //  var modalInstance = $modal.open({
    //      templateUrl: 'views/partials/checkoutModal.html',
    //      controller: 'CheckoutModalInstanceCtrl'
    //  });
    //
    //  modalInstance.result.then(function(){
    //    // console.log('tes');
    //    blockUI.start();
    //    // AngularLoaderBlocker.enable();
    //    Orders.checkoutOrders();
    //  });
    //}

    // initialize model
    $scope.pendingBets = [];
    $scope.cost = {};
    Orders.refreshOrders();

    // get initial list of bets
    Bets.retrievePendingBets();

    // refresh bets list from service
    $scope.$watch(function(){
        return Bets.getPendingBets();
    }, function(data){
        $scope.pendingBets = data;
    });

        $scope.$watch(function(){
        return Bets.getCost();
    }, function(data){
        $scope.cost = data;
    });

    // retrieve latest bets list from server every interval
    var interval = 5000; // interval in ms
    refreshPendingBets();
    function refreshPendingBets() {
        Bets.retrievePendingBets();
        Bets.retrievePendingBetsCost();
        //$scope.UTIL.setTimeout($scope, refreshPendingBets, interval);
    }

    // mark pending bet as deleted
    $scope.removePendingBet = function(betId) {
        Bets.removePendingBet(betId);
    };

    // start process bet flow with all pending bets
    $scope.purchaseAllPendingBets = function() {

    };

});

angular.module('lotteryApp')
  .controller('CheckoutModalInstanceCtrl', function ($scope, $modalInstance, Orders) {

  $scope.orders = Orders.getOrders();
  $scope.totalAmount = Orders.getTotalAmount();

  $scope.checkoutOk = function() {
    $modalInstance.close();
  }

  $scope.cancel = function () {
    $modalInstance.dismiss('cancel');
  };
});
