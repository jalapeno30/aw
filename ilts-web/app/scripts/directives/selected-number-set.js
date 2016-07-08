/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: directives\selected-number-set.js
 */
'use strict';

angular.module('lotteryApp')
    .directive('selectedNumberSet', selectedNumberSet);

function selectedNumberSet(LotteryGame, $rootScope) {
    var directive = {
        restrict: 'E',
        replace: true,
        templateUrl: 'views/partials/selected-number-set.html',
        controller: selectedNumberSetController,
        controllerAs: 'selectedSet',
        link: selectedNumberSetLink,
        scope: {},
        bindToController: {
            system: '=',
            game: '='
        }
    };

    return directive;

    function selectedNumberSetController($scope) {
        var vm = this;
        vm.numberRange = [];
        vm.numberSets = LotteryGame.getNumberSets(vm.game.gameId);
        vm.currentSet = 0;
        vm.luckyPicks = LotteryGame.getLuckyNumberSets(vm.game.gameId);

        $rootScope.$on('changeNumberSet-' + vm.game.gameId, function(e, numSets) {
            $scope.$evalAsync(function() {
                vm.numberSets = numSets;
                vm.luckyPicks = LotteryGame.getLuckyNumberSets(vm.game.gameId);
            });
        });

        vm.chooseCurrent = function(idx) {
            vm.currentSet = idx;
            LotteryGame.setCurrentNumberSet(vm.game.gameId, idx);
        };

        vm.isLucky = function(idx) {
            return vm.luckyPicks.indexOf(idx)>-1;
        };
        
        
        vm.clear = function(idx) {
            LotteryGame.assignNumberSetToCurrentSet(vm.game.gameId, []);
            if(vm.isLucky(idx)){
                LotteryGame.toggleLuckyNumberSet(vm.game.gameId, idx);
            };

        };
    }

    function selectedNumberSetLink(scope, elem, attrs, ctrl) {
        scope.$watch(function() {return ctrl.system;}, function(newVal) {
            if (newVal) {
                ctrl.numberRange = [];
                for (var i=0; i<newVal.systemNumber; i++) {
                    ctrl.numberRange.push(i);
                }
            }
        });
    }
}
