'use strict';

describe('directive: game-number-set', function(){
  var vm;
  var el;
  var scope;
  var template;

  var numbers = 42;
  var system = {
    id: '6',
    numbers: 6
  };
  var game = {
    id: 1
  }

  var html = '<game-number-set numbers="{{numbers}}" system="system" game="game"></game-number-set>';

  beforeEach(module('lotteryApp', 'templates'));

  beforeEach(inject(function($templateCache, $compile, $rootScope){
    template = $templateCache.get('app/views/partials/game-number-set.html');
    $templateCache.put('views/partials/game-number-set.html', template);
    scope = $rootScope.$new();
    scope.numbers = numbers;
    scope.system = system;
    scope.game = game;

    el = angular.element(html);
    $compile(el)(scope);
    scope.$digest();
    vm = el.isolateScope().numberSet;
  }));

  it('should compile', function(){
    expect(el.html()).toContain('table')
  });

  describe('model', function(){

    it('should have public attributes', function(){
      expect(vm.numberSets).toBeDefined();
      expect(vm.currentNumberSet).toBeDefined();
    });

    it('should have correct initial values', function(){
      expect(vm.currentNumberSet).toEqual([]);
    });

  });

  describe('public methods', function(){

    describe('isSelected', function(){

      it('should exist', function(){
        expect(vm.isSelected).toBeDefined();
      });

      it('should indicate whether a number is in the current number set', function(){
        vm.currentNumberSet = [1, 2, 3, 4];
        expect(vm.isSelected(1)).toEqual(true);
        expect(vm.isSelected(5)).toEqual(false);
      });

    });

    describe('init', function(){

      it('should exist', function(){
        expect(vm.init).toBeDefined();
      });

    });

  });

  describe('helper methods', function(){

    describe('_toggleNumberSelect', function(){

      it('should exist', function(){
        expect(_toggleNumberSelect).toBeDefined();
      });

      it('should toggle class of selected number', function(){
        _toggleNumberSelect(1, el);
        var gameNumber = el.find('td.game_number[number="1"]');
        expect(gameNumber.hasClass('selected-in-table')).toEqual(true);
        _toggleNumberSelect(1, el);
        expect(gameNumber.hasClass('selected-in-table')).toEqual(false);
      });

    });

    describe('_generateSplitNumbers', function(){

      it('should exist', function(){
        expect(_generateSplitNumbers).toBeDefined();
      });

      it('should generate an array of numbers in groups of 10', function(){
        var numbers = 22;
        var expected = [
          [1,2,3,4,5,6,7,8,9,10],
          [11,12,13,14,15,16,17,18,19,20],
          [21,22]
        ];
        expect(_generateSplitNumbers(numbers)).toEqual(expected);
      });

    });

  });

  describe('link methods', function(){

    describe('selectNumber', function(){

      it('should exist', function(){
        expect(vm.selectNumber).toBeDefined();
      });

      it('should deselect a previously chosen number', inject(function(LotteryGame){
        vm.currentNumberSet = ['1', '2', '3'];

        spyOn(LotteryGame, 'removeNumberFromCurrSet');
        vm.selectNumber(1);
        expect(LotteryGame.removeNumberFromCurrSet).toHaveBeenCalledWith(game.id, 1);
      }));

      it('should add selected number to current selection if there is still space', inject(function(LotteryGame){
        vm.currentNumberSet = [1, 2, 3];

        spyOn(LotteryGame, 'getCurrentNumberSet').and.returnValue(vm.currentNumberSet);
        spyOn(LotteryGame, 'addNumberToCurrSet');
        vm.selectNumber(4);
        expect(LotteryGame.addNumberToCurrSet).toHaveBeenCalledWith(game.id, 4);
      }));

      it('should add selected number to current selection and remove first number if there is no more space', inject(function(LotteryGame){
        vm.currentNumberSet = [1, 2, 3];
        vm.system.numbers = 3;

        spyOn(LotteryGame, 'getCurrentNumberSet').and.returnValue(vm.currentNumberSet);
        spyOn(LotteryGame, 'removeNumberFromCurrSet');
        spyOn(LotteryGame, 'addNumberToCurrSet');
        vm.selectNumber(4);
        expect(LotteryGame.removeNumberFromCurrSet).toHaveBeenCalledWith(game.id, 1);
        expect(LotteryGame.addNumberToCurrSet).toHaveBeenCalledWith(game.id, 4);
      }));

    });

  });

  describe('watchers', function(){

    describe('system', function(){

      it('should watch for system model change and trim numbers if they exceed the new system limit', inject(function(LotteryGame){
        vm.currentNumberSet = [1, 2, 3, 4];
        vm.system = {
          numbers: 3
        };
        spyOn(LotteryGame, 'getCurrentNumberSet').and.returnValue(vm.currentNumberSet);
        spyOn(LotteryGame, 'removeNumberFromCurrSet');
        scope.$apply();
        expect(LotteryGame.removeNumberFromCurrSet).toHaveBeenCalledWith(game.id, 4);
      }));

    });

  });

});
