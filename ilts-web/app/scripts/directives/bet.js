/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: directives\bets.js
 */
'use strict';

angular
    .module('lotteryApp')
    .directive('bet', bet)
    .directive('betGame', betGame);

function bet(LotteryGames, LotteryGame) {
    var directive = {
        restrict: 'E',
        scope: {},
        bindToController: {},
        replace: true,
        controller: betController,
        controllerAs: 'bet',
        link: betLink,
        templateUrl: 'views/directives/bet.html'
    };

    return directive;

    function betController() {
        var vm = this;
        vm.games = [];
        //alert lottery game of game change
        vm.gameChange= function() {
            LotteryGame.setFlag();
        };
        
        vm.init = function() {
            // fetch games
            LotteryGames.fetchGames().then(function(){
                // attach games to vm
                vm.games = LotteryGames.getGames();
            });
        };
    }

    function betLink(scope, elem, attr, ctrl) {
        ctrl.init();
    }
}

function betGame(LotteryGame, $modal, Order, Orders, growl) {
    var directive = {
        restrict: 'E',
        scope: {},
        bindToController: {
            game: '='
        },
        replace: true,
        controller: betGameController,
        controllerAs: 'betGame',
        link: betGameLink,
        templateUrl: 'views/directives/bet-game.html'
    };

    return directive;

    function betGameController($scope) {
        var vm = this;
        vm.draws = [];
        vm.systems = [];
        vm.displayNumbers = [];
        vm.auto;
        vm.lucky;
        vm.selectedDraws;
                
        vm.init = function() {
            LotteryGame.fetchDraws(vm.game.gameId).then(function() {
                vm.draws = LotteryGame.getDraws();
            });
            // fetch systems
            LotteryGame.fetchSystems().then(function() {
                vm.systems = LotteryGame.getSystems();
                vm.activeSystem = vm.systems[1];
                vm.system = vm.activeSystem.systemName;
            });
            //this controls the length of each row in number grid
            LotteryGame.fetchDisplayNumbers(vm.game.gameId).then(function() {
                vm.displayNumbers = LotteryGame.getDisplayNumbers()[0];
                vm.auto=LotteryGame.getAuto();
                vm.lucky=LotteryGame.getLucky();
            });

            LotteryGame.initNumberSets(vm.game.gameId);
        };

        vm.autoPlay = function() {
            //only fire auto play function if numberset isn't lucky
            if(!LotteryGame.currentIsLucky(vm.game.gameId)){
                var randomSet = randomNumberSet(vm.game.gameNumbers, vm.activeSystem.systemNumber);
                LotteryGame.assignNumberSetToCurrentSet(vm.game.gameId, randomSet); 
            }else{
                growl.addErrorMessage("Clear selection to play Auto Pick");
            };     
        };
        
        //don't lucky pick if number set already has selected numbers or autoplay
        vm.luckyPick = function() {
            if(LotteryGame.canLucky(vm.game.gameId)){
                var randomSet = randomNumberSet(vm.game.gameNumbers, vm.activeSystem.systemNumber);
                LotteryGame.assignNumberSetToCurrentSet(vm.game.gameId, randomSet);
                LotteryGame.toggleLuckyNumberSet(vm.game.gameId);
            }
            else{
                growl.addErrorMessage("Clear selection to play Lucky Pick");
            }
        };
        
//        vm.addOrder = function() {
//            if (vm.selectedDraws && Object.keys(vm.selectedDraws).length > 0) {
//                for (var key in vm.selectedDraws) {
//                    if (vm.selectedDraws[key]) {
//                        var order = new Order();
//                        order.setGameID(vm.game.gameId);
//                        order.setDrawID(key);
//                        order.setNumbers(LotteryGame.getNumberSets(vm.game.gameId).filter(function(numSet) {
//                            return numSet.length > 0;
//                        }));
//                        order.setSystem(vm.activeSystem);
//                        console.log(order);
//                        Orders.addPendingOrder(order);
//                    }
//                }
//                Orders.sendPendingOrders();
//                Orders.refreshOrders();
//                LotteryGame.initNumberSets(vm.game.gameId);
//            } else if (LotteryGame.getNumberSets(vm.game.gameId).filter(function(numSet) {
//                return numSet.length > 0;
//            }).length === 0) {
//                growl.addErrorMessage('Please select at least one number set.');
//            } else {
//                growl.addErrorMessage('Please select at least one draw.');
//            }
//        };
        
        //changed the addOrder function
        vm.addOrder = function() {
            var numbSets = LotteryGame.getNumberSets(vm.game.gameId)
            var totalNumSets = LotteryGame.getNumberSets(vm.game.gameId).length;
            var systemNum = vm.activeSystem.systemNumber;            
            var emptyCheck = 0;
            var tooFewNumbers = false;           
            
            //make sure each number set has enough numbers, or is empty before submittng
            var i=0;
            for (i=0; i< totalNumSets; i++){
                emptyCheck += numbSets[i].length;
                if(numbSets[i].length > 0  &&  numbSets[i].length < systemNum){
                    tooFewNumbers=true;
                }
            }

            //make sure there is atleast one number set (empty check), make sure all number sets are full, make sure a draw is selected
            if(emptyCheck > 0 && !tooFewNumbers && vm.selectedDraws){                 
                for (var key in vm.selectedDraws) {
                    if (vm.selectedDraws[key]){
                        var order = new Order();
                        order.setGameID(vm.game.gameId);
                        order.setDrawID(key);
                        order.setNumbers(LotteryGame.getNumberSets(vm.game.gameId).filter(function(numSet) {
                            return numSet.length > 0;
                        }));
                        order.setSystem(vm.activeSystem);
                        Orders.addPendingOrder(order);   
                    }
                }
                Orders.sendPendingOrders();
                Orders.refreshOrders();
                LotteryGame.initNumberSets(vm.game.gameId);
            }else if(emptyCheck == 0){
                growl.addErrorMessage('Select at least one number set.');
            }else if(tooFewNumbers){
                growl.addErrorMessage('Select ' + systemNum + ' numbers for each numbet set' );
            }else growl.addErrorMessage('Select Draw'); 
        };
        
        vm.changeSystem =function(newSys){
            var gNS = LotteryGame.getNumberSets(vm.game.gameId);
            var content=0
            
            //check to see if any numbersets contain numbers
            for (var i = 0; i < gNS.length; i++){
                if(gNS[i].length > 0){
                    content++;
                }  
            }
            
            //if all sets are empty change the system
            if(content == 0){
                vm.activeSystem = newSys;
            }else{
                //else verify that the user wants to change
                bootbox.confirm({ 
                    size: 'small',
                    message: "Are you sure you want to change your selection?", 
                    callback: function(result){
                        //if yes, delete the lucky picks
                        if(result == true){
                            var luckyRows = LotteryGame.getLuckyNumberSets(vm.game.gameId);
                            var lR;
                            for (lR = 0; lR < luckyRows.length; lR++) {
                                LotteryGame.deleteLucky(vm.game.gameId, luckyRows[lR]);
                            }
                            $scope.$apply(vm.activeSystem = newSys); 
                        }
                    }
                })
            }
        };
    }

    function betGameLink(scope, elem, attr, ctrl) {
        ctrl.init(ctrl.game);

        scope.$watch(function() {
            return ctrl.system;
        }, function(newVal) {
            ctrl.activeSystem = ctrl.systems.filter(function(system) {
                return system.systemName === newVal;
            })[0];
        });
    }
}

function randomNumberSet(max, numbers) {
    var i, arr = [];
    for (i = 0; i < max; i++) {
        arr[i] = i + 1;
    }

    var shuffled = shuffle(arr);
    return shuffled.slice(0, numbers).sort(function(a,b){return a - b}).map(function(n) {
        return n;
    });
}

function shuffle(array) {
    var currentIndex = array.length, temporaryValue, randomIndex;

    // While there remain elements to shuffle...
    while (0 !== currentIndex) {
        // Pick a remaining element...
        randomIndex = Math.floor(Math.random() * currentIndex);
        currentIndex -= 1;

        // And swap it with the current element.
        temporaryValue = array[currentIndex];
        array[currentIndex] = array[randomIndex];
        array[randomIndex] = temporaryValue;
    }

    return array;
}