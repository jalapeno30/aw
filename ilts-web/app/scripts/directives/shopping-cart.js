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
    vm.toggleSmall = (vm.toggle == "small");
 
    vm.classSwitch = function(){
        var mainWidth = ["col-xs-12", "col-xs-hidden col-sm-6 orders-content"]
        var ticketsPer = ["col-lg-4 col-md-6", "col-xs-6 col-lg-6 orders-content"]
        if(vm.toggleSmall != true){
            return([mainWidth[0], ticketsPer[0]])
        }else{
            return([mainWidth[1], ticketsPer[1]])
        }
    };
    
    console.log(vm.classSwitch());

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
