'use strict';

describe('Filter: languageKeys', function () {

  // load the filter's module
  beforeEach(module('lotteryApp'));

  // initialize a new instance of the filter before each test
  var languageKeys;
  beforeEach(inject(function ($filter) {
    languageKeys = $filter('languageKeys');
  }));

  //it('should return the input prefixed with "languageKeys filter:"', function () {
  //  var text = 'angularjs';
  //  expect(languageKeys(text)).toBe('languageKeys filter: ' + text);
  //});

});
