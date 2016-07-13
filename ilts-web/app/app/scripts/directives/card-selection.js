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
  }

  function cardSelectionLink(scope, elem, attr, ctrl) {

  }
}

