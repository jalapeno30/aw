'use strict';

describe('Filter: userRestrictedNav', function () {

  // load the filter's module
  beforeEach(module('lotteryApp'));

  // initialize a new instance of the filter before each test
  var userRestrictedNav;
  beforeEach(inject(function ($filter) {
    userRestrictedNav = $filter('userRestrictedNav');
  }));

  //it('should return the input prefixed with "userRestrictedNav filter:"', function () {
  //  var text = 'angularjs';
  //  expect(userRestrictedNav(text)).toBe('userRestrictedNav filter: ' + text);
  //});

});
