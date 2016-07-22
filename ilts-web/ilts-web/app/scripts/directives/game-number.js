/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: directives\game-number.js
 */
'use strict';

angular.module('lotteryApp')
    .directive('gameNumber', gameNumber);

function gameNumber() {
    var directive = {
        scope: {},
        bindToController: {
            number: '='
        },
        controller: gameNumberController,
        controllerAs: 'gameNumber',
        link: gameNumberLink,
        require: ['^gameNumberSet', 'gameNumber']
    };

    return directive;

    function gameNumberController() {
        var vm = this;
        vm.isSelected;
    }

    function gameNumberLink(scope, elem, attrs, ctrl) {
        elem.bind('click', function(){
            ctrl[0].selectNumber(ctrl[1].number);
        });
    }
}