'use strict';

angular.module('lotteryApp')
  .directive('validNumber', function () {
    return {
     require: '?ngModel',
     link: function(scope, element, attrs, modelCtrl) {
       modelCtrl.$parsers.push(function (inputValue) {

           if (inputValue == undefined) {
               return '';
           }

           var firstParse = inputValue.replace(/[^0-9 . -]/g, '');

           var safeParse = firstParse.charAt(0);
           var prepParse = firstParse.substring(1,firstParse.length);

           var secondParse = safeParse + prepParse.replace(/[^0-9 .]/g, '');

           var n = secondParse.indexOf(".");
           var transformedInput;

           if (n == -1) {
               transformedInput = secondParse;
           }
           else {
               safeParse = secondParse.substring(0,n+1);
               firstParse = (secondParse.substring(n+1,secondParse.length)).replace(/[^0-9]/g, '');
               n = 2;

               if (firstParse.length <= n) {
                   transformedInput = safeParse + firstParse;
               }
               else {
                   transformedInput = safeParse + firstParse.substring(0,n);
               }
           }

           if (transformedInput!=inputValue) {

               var returnValue;

               returnValue=transformedInput;


               modelCtrl.$setViewValue(returnValue);
               modelCtrl.$render();
           }
          console.log(transformedInput, returnValue);
          //  return returnValue;
          return transformedInput;
       });
     }
   };
  });
