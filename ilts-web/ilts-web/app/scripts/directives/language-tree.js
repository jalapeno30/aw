'use strict';

angular.module('lotteryApp')
  .directive('languageTree', function (RecursionHelper, $compile) {

    function renderTemplate(elem, attr) {
      console.log(elem, attr);
      elem.html(attr.value);
    }

    function link(scope, element, attrs) {
      console.log(scope, element, attrs);
    }

    return {

      restrict: 'E',
      scope: {
        tree: '='
      },
      template:
        '<p ng-if="typeof(tree) == \'object\'">Test</p>' +
        '<p ng-if="typeof(tree) != \'object\'">{{ tree }}</p>',
      compile: function(element) {
        return RecursionHelper.compile(element);
      }

    };
  });
