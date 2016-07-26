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
    self.newCard;
    self.existingCards;
    self.buttonSwitch;
    
    self.click=function(string){
       self.buttonSwitch = string;
    }       
//    temporary
    self.cardList={
        one:   {name: 'visa',number: '02020202'},
        two:   {name: 'visa', number: '888888'},
        three: {name: 'master', number: '654654654'},        
    };
    
    self.months = [1,2,3,4,5,6,7,8,9,10,11,12]
    self.years = function(){
    var yearRange = [];
    for (var i = 2016; i <= 2036; i ++) yearRange.push(i);
    return yearRange;
    };
    
    self.pop = function(test){
        bootbox.alert(test)
    }
  }
  
  function cardSelectionLink(scope, elem, attr, ctrl) {

  }
}

