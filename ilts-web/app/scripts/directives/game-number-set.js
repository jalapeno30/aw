/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 * change
 * File: directives\game-number-set.js
 */
'use strict';

angular.module('lotteryApp')
    .directive('gameNumberSet', gameNumberSet);

function gameNumberSet(LotteryGame, $timeout) {
    var directive = {
        restrict: 'E',
        templateUrl: 'views/partials/game-number-set.html',
        controller: gameNumberSetController,
        controllerAs: 'numberSet',
        link: gameNumberSetLink,
        scope: {},
        bindToController: {
            numbers: '=',
            system: '=',
            game: '='
        }
    };

    return directive;

    function gameNumberSetController() {
        var vm = this;
        vm.numberSets = [];
        vm.currentNumberSet = [];
        vm.currentIsLucky = false;
        vm.split;
        vm.alertMessage="";
        vm.alert=false;
        vm.isSelected = function(number) {
            return vm.currentNumberSet.indexOf(number)>-1;
        }

        vm.init = function () {
            LotteryGame.fetchDisplayNumbers(vm.game.gameId).then(function() {
                vm.displayNumbers = LotteryGame.getDisplayNumbers()[0];
                //determines length of row before split
                vm.split=LotteryGame.getNumberDisplay();
                vm.numberSets = _generateSplitNumbers(vm.numbers, vm.split);
                LotteryGame.initNumberSets(vm.game.gameId)
            });          
        };
    }

    function gameNumberSetLink(scope, elem, attrs, ctrl) {
        // watch for change in game system
        // this affects the number of elements of selected numbers 
        scope.$watch(function(){
            return ctrl.system;
        }, function(newVal) {
            // check if new system has less cardinality as the current number set length
            var numSetLength = LotteryGame.getCurrentNumberSet(ctrl.game.gameId).length;
            if (!!newVal && newVal.systemNumber < numSetLength/*ctrl.currentNumberSet.length*/) {
                // remove last n selected numbers from selected number set
                for (var i=numSetLength; i>newVal.systemNumber; i--) {
                    var lastNum = LotteryGame.getCurrentNumberSet(ctrl.game.gameId)[i-1];
                    LotteryGame.removeNumberFromCurrSet(ctrl.game.gameId, lastNum);
                    _toggleNumberSelect(lastNum, elem);
                }
            }
        });

        scope.$watch(function() {
            return LotteryGame.getCurrentNumberSet(ctrl.game.gameId);
        }, function(nV) {
            ctrl.currentNumberSet = nV;
            ctrl.currentIsLucky = LotteryGame.currentIsLucky(ctrl.game.gameId);
        });

        ctrl.init();

        ctrl.selectNumber = function (number) {             
            // check if clicked game number is already selected 
            if (ctrl.currentNumberSet.indexOf(number)>-1) {
                var numSet = angular.copy(ctrl.currentNumberSet);
                // remove number from selected set
                LotteryGame.removeNumberFromCurrSet(ctrl.game.gameId, number);
            } else {
                // check if there is still available slots for selected number set
                if (LotteryGame.getCurrentNumberSet(ctrl.game.gameId).length < ctrl.system.systemNumber) {
                    // add to selected number set
                    LotteryGame.addNumberToCurrSet(ctrl.game.gameId, number);
                } else {
                    scope.$apply(function(){
                        ctrl.alertMessage="You have reached maximum numbers allowed for selection";
                        ctrl.alert=true;
                        $timeout(function () { ctrl.alert = false; }, 2000);  
                        console.log(ctrl.alert);
                    })

                     //remove first element and add selected number NOT NEEDED ANYMORE  
//                    var firstNumber = LotteryGame.getCurrentNumberSet(ctrl.game.gameId)[0];
//                    LotteryGame.removeNumberFromCurrSet(ctrl.game.gameId, firstNumber);
//                    LotteryGame.addNumberToCurrSet(ctrl.game.gameId, number);
                }
            }
        };
    }
}

/**
 * toggle game number as selected or not
 * @param number
 * @param elem
 * @private
 */
function _toggleNumberSelect(number, elem) {
    var className = 'selected-in-table';
    var gameNumber = elem.find('td.game_number[number="' + number + '"]');
    if (gameNumber.hasClass(className)) {
        gameNumber.removeClass(className);
    } else {
        gameNumber.addClass(className);
    }
}

/**
 * generate numberSet grouped by 10 from a range change
 * @param numbers
 * @returns {Array}
 * @private
 */
function _generateSplitNumbers(numbers, splitNumber) {
    var splitNumbers = [];
    var numberSets = [];
    //generate number selection grid, up to total game number length
    for (var i = 1; i <= numbers; i++) {
        splitNumbers.push(i);
        //once we reach a number divisible by split number make a new row
        if (i % splitNumber== 0) {
            numberSets.push(splitNumbers);
            splitNumbers = [];
        }
        //push at end 
           if (i == numbers && splitNumbers.length > 0) {
            numberSets.push(splitNumbers);
        }
    }

    return numberSets;
}