'use strict';

describe('directive: selected-number-set', function(){
  var vm;
  var el;
  var scope;
  var template;

  var system = {
    id: '6',
    numbers: 6
  };

  var html = '<selected-number-set system="system"></selected-number-set>';

  beforeEach(module('lotteryApp', 'templates'));

  beforeEach(inject(function($templateCache, $compile, $rootScope){
    template = $templateCache.get('app/views/partials/selected-number-set.html');
    $templateCache.put('views/partials/selected-number-set.html', template);
    scope = $rootScope.$new();
    scope.system = system;

    el = angular.element(html);
    $compile(el)(scope);
    scope.$digest();
    vm = el.isolateScope().selectedSet;
  }));
});
