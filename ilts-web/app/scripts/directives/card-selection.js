'use strict';

angular
  .module('lotteryApp')
  .directive('cardSelection', cardSelection);

function cardSelection() {
  var directive = {
    restrict: 'E',
    controller: cardSelectionController,
    controllerAs: 'cardSelection',
    link: cardSelection,
    templateUrl: 'views/directives/card-selection.html'
  };

  return directive;

  function cardSelectionController() {
    var vm = this;
    vm.buttonSwitch;
    
    vm.click=function(string){
       vm.buttonSwitch = string;
    }
    
    vm.toggle = function(string){
        return string == vm.buttonSwitch ? true : false;
    }
  }

  function cardSelectionLink(scope, elem, attr, ctrl) {

  }
}

