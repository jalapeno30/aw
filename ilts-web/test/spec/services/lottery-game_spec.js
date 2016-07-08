'use strict';

describe('service: LotteryGames', function(){
  var _LotteryGames_, _$httpBackend_, _ENV_;

  beforeEach(module('lotteryApp'));

  beforeEach(inject(function(LotteryGames, $httpBackend, ENV){
    _LotteryGames_ = LotteryGames;
    _$httpBackend_ = $httpBackend;
    _ENV_ = ENV;
  }));

  describe('public methods', function(){

    describe('fetchGames', function(){

      it('should exist', function(){
        expect(_LotteryGames_.fetchGames).toBeDefined();
      });

    });

    describe('getGames', function(){

      it('should exist', function(){
        expect(_LotteryGames_.getGames).toBeDefined();
      });

      it('should return empty array initially', function(){
        expect(_LotteryGames_.getGames()).toEqual([]);
      });

      it('should return array of games after fetch', function(){
        var testData = [
          {
            id: 1,
            name: 'Test'
          }
        ]
        _$httpBackend_.when('GET', _ENV_.apiEndpoint + '/games')
          .respond(200, testData);
        _LotteryGames_.fetchGames().then(function(){
          expect(_LotteryGames_.getGames()).toEqual(testData);
        });
        _$httpBackend_.flush();
      });

    });

  });

});

describe('service: LotteryGame', function(){
  var _LotteryGame_, _$httpBackend_, _ENV_;

  beforeEach(module('lotteryApp'));

  beforeEach(inject(function(LotteryGame, $httpBackend, ENV){
    _LotteryGame_ = LotteryGame;
    _$httpBackend_ = $httpBackend;
    _ENV_ = ENV;
  }));

  describe('public methods', function(){

    describe('fetchDraws', function(){

      it('should exist', function(){
        expect(_LotteryGame_.fetchDraws).toBeDefined();
      });

    });

    describe('getDraws', function(){

      it('should exist', function(){
        expect(_LotteryGame_.getDraws).toBeDefined();
      });

      it('should return empty array initially', function(){
        expect(_LotteryGame_.getDraws()).toEqual([]);
      });

      it('should return array of draws after fetch', function(){
        var testData = [
          {
            id: 1,
            name: 'Test'
          }
        ]
        _$httpBackend_.when('GET', _ENV_.apiEndpoint + '/games/1/draws/available')
          .respond(200, testData);
        _LotteryGame_.fetchDraws(1).then(function(){
          expect(_LotteryGame_.getDraws()).toEqual(testData);
        });
        _$httpBackend_.flush();
      });

    });

    describe('fetchSystems', function(){

      it('should exist', function(){
        expect(_LotteryGame_.fetchSystems).toBeDefined();
      });

    });

    describe('getSystems', function(){

      it('should exist', function(){
        expect(_LotteryGame_.getSystems).toBeDefined();
      });

      it('should return empty array initially', function(){
        expect(_LotteryGame_.getSystems()).toEqual([]);
      });

      it('should return array of draws after fetch', function(){
        var testData = [
          {
            id: 1,
            name: 'Test'
          }
        ]
        _$httpBackend_.when('GET', _ENV_.apiEndpoint + '/games/systems')
          .respond(200, testData);
        _LotteryGame_.fetchSystems(1).then(function(){
          expect(_LotteryGame_.getSystems()).toEqual(testData);
        });
        _$httpBackend_.flush();
      });

    });

    describe('getNumberSets', function(){

      it('should exist', function(){
        expect(_LotteryGame_.getNumberSets).toBeDefined();
      });

    });

    describe('initNumberSets', function(){

      it('should exist', function(){
        expect(_LotteryGame_.initNumberSets).toBeDefined();
      });

      it('shouldfill in the number sets array with an array of empty arrays', function(){
        var testId = 1;
        var expected = [[], [], [], [], [], []];
        _LotteryGame_.initNumberSets(testId);
        expect(_LotteryGame_.getNumberSets(testId)).toEqual(expected);
      });

    });

    describe('getCurrentNumberSet', function(){

      it('should exist', function(){
        expect(_LotteryGame_.getCurrentNumberSet).toBeDefined();
      });

      it('should return an empty array initially', function(){
        var testId = 1;
        _LotteryGame_.initNumberSets(testId);
        expect(_LotteryGame_.getCurrentNumberSet(testId)).toEqual([]);
      });

    });

    describe('addNumberToCurrSet', function(){

      it('should exist', function(){
        expect(_LotteryGame_.addNumberToCurrSet).toBeDefined();
      });

      it('should add the number to the current empty number set', function(){
        var testId = 1;
        var testNumber = 13;
        var expected = [13];
        _LotteryGame_.initNumberSets(testId);
        _LotteryGame_.addNumberToCurrSet(testId, testNumber);
        expect(_LotteryGame_.getCurrentNumberSet(testId)).toEqual(expected);
      });

    });

    describe('removeNumberFromCurrSet', function(){

      it('should exist', function(){
        expect(_LotteryGame_.removeNumberFromCurrSet).toBeDefined();
      });

      it('should remove the number from the current number set', function(){
        var testId = 2;
        _LotteryGame_.initNumberSets(testId);
        _LotteryGame_.addNumberToCurrSet(testId, 13);
        _LotteryGame_.addNumberToCurrSet(testId, 26);
        _LotteryGame_.addNumberToCurrSet(testId, 39);
        expect(_LotteryGame_.getCurrentNumberSet(testId)).toEqual([13, 26, 39]);
        _LotteryGame_.removeNumberFromCurrSet(testId, 39);
        expect(_LotteryGame_.getCurrentNumberSet(testId)).toEqual([13, 26]);
        _LotteryGame_.removeNumberFromCurrSet(testId, 13);
        expect(_LotteryGame_.getCurrentNumberSet(testId)).toEqual([26]);
        _LotteryGame_.removeNumberFromCurrSet(testId, 26);
        expect(_LotteryGame_.getCurrentNumberSet(testId)).toEqual([]);
      });

    });

    describe('setCurrentNumberSet', function(){

      it('should exist', function(){
        expect(_LotteryGame_.setCurrentNumberSet).toBeDefined();
      });

      it('should set the number set to current', function(){
        var testId = 2;
        _LotteryGame_.initNumberSets(testId);
        _LotteryGame_.addNumberToCurrSet(testId, 13);
        _LotteryGame_.addNumberToCurrSet(testId, 26);
        expect(_LotteryGame_.getCurrentNumberSet(testId)).toEqual([13, 26]);
        _LotteryGame_.setCurrentNumberSet(testId, 1);
        expect(_LotteryGame_.getCurrentNumberSet(testId)).toEqual([]);
        _LotteryGame_.addNumberToCurrSet(testId, 39);
        expect(_LotteryGame_.getCurrentNumberSet(testId)).toEqual([39]);
        _LotteryGame_.setCurrentNumberSet(testId, 0);
        expect(_LotteryGame_.getCurrentNumberSet(testId)).toEqual([13, 26]);
      });

    });


  });

});
