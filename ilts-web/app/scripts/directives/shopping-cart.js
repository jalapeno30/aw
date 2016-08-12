'use strict';

angular
  .module('lotteryApp')
  .directive('shoppingCart', shoppingCart);

function shoppingCart(Orders, Payment, User) {
  var directive = {
    restrict: 'E',
    replace: true,
    scope: true,
    bindToController: {
      toggle: '@'
    },
    controller: shoppingCartController,
    controllerAs: 'shoppingCart',
    link: shoppingCartLink,
    templateUrl: 'views/directives/shopping-cart.html'
  };

  return directive;

  function shoppingCartController($scope, $location) {
    var vm = this;
    vm.token = User.getToken();
    vm.userId = User.getId();
    vm.orderIds;
    vm.orders; 
    vm.cost;
    vm.toggleScreen = (vm.toggle === "payment");
 
    //This detirmes what elements to show based on toggle above (in bind to controller)
    vm.classSwitch = function(){
        return(vm.toggleScreen === true ? "hidden" : "show" )
    };
    
    vm.border = function(){
        if (vm.toggleScreen){
            return {"border": "none"}
        }      
    };

    Orders.refreshOrders();
    $scope.$watch(function(){
      return Orders.getOrders();
    }, function(n, o){
      vm.orders = n;
      vm.orderIds = Orders.getOrderIds();
      vm.cost = Orders.getTotalAmount();
    }, true);

    vm.removeOrder = function(bet_id) {
      Orders.removeOrder(bet_id);
    };
    
    vm.spaghetti = function() {
      Orders.removeAllOrders(vm.orderIds);
    };

    //iteration03 this sends orderId to the payment service and directs user to payment wizard
    vm.checkout = function() {
        Payment.purchaseBet(vm.token, vm.userId, vm.orderIds).then(function(response){           
            if(response.data){
                if(response.data.transId){
                    Payment.transId = response.data.transId;
                    $location.path('/payment');//sending orderIds, saving transactionId
                }else{
                    bootbox.alert("error");//no transactionId came back
                }
            }
            else {
                bootbox.alert('Error processing order');//call to API didnt work
            };
        });
    };
  }

  function shoppingCartLink(scope, elem, attr, ctrl) {

  }
}
