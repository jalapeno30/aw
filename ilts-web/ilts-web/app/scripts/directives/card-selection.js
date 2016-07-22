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
    var self = this;
    self.buttonSwitch;
    
    self.click=function(string){
       self.buttonSwitch = string;
    }
    
    
//    temporary
    self.cardList={
        one:   {name: 'visa',number: 123456789},
        two:   {name: 'visa', number: 123456789},
        three: {name: 'master', number: 123456789},        
    };
    
  }

  function cardSelectionLink(scope, elem, attr, ctrl) {

  }
}

