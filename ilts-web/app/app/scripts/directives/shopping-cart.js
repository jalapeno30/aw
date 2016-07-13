'use strict';

angular
  .module('lotteryApp')
  .directive('shoppingCart', shoppingCart);

function shoppingCart(Orders) {
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

  function shoppingCartController($scope) {
    var vm = this;
    vm.orders;      
    vm.toggleScreen = (vm.toggle === "payment");
 
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
      vm.cost = Orders.getTotalAmount();
    }, true);

    vm.removeOrder = function(bet_id) {
      Orders.removeOrder(bet_id);
    }

    vm.checkout = function() {
      Orders.getGreeting();
      Orders.checkoutOrders();
    }
  }

  function shoppingCartLink(scope, elem, attr, ctrl) {

  }
}
