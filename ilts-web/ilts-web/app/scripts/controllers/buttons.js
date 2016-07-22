'use strict';

angular.module('lotteryApp')
  .controller('ButtonsCtrl', function ($scope, Game) {
    $scope.buttons = [
      {
      	"name" : "5R",
        "value" : 5
      },
      {
      	"name" : "6",
        "value" : 6
      },
      {
      	"name" : "7",
        "value" : 7
      },
      {
      	"name" : "8",
        "value" : 8
      },
      {
      	"name" : "9",
        "value" : 9
      },
      {
        "name" : "10",
        "value" : 10
      },
      {
        "name" : "11",
        "value" : 11
      },
      {
        "name" : "12",
        "value" : 12
      }
    ];

    $scope.radio = {"model" : '6'};
    // $scope.system = 6;
    // $scope.sysName = "6";

    var gameObj = $scope.$parent.game.game; 
    // console.log($scope.$parent.game.game);
    gameObj.setSystem({
      "name" : "6",
      "value" : 6
    });
    $scope.system = gameObj.getSystem();
    // Game.setSelectableNumbers(6);

    $scope.$watch(function(){
      return gameObj.getSystem();
    }, function(newVal, oldVal){
      $scope.system = newVal;
    }, true);

    $scope.updateSystem = function(button) {
      for (var i = 0; i < $scope.buttons.length; i++) {
        if (button == $scope.buttons[i].name) {
          // $scope.system = $scope.buttons[i].value;
          // $scope.sysName = $scope.buttons[i].name;
          // $scope.$parent.game.setSystem($scope.buttons[i]);
          gameObj.setSystem($scope.buttons[i]);
          gameObj.setTitle('test');
          // console.log(gameObj);
          break;
        }
      }
      // Game.setSelectableNumbers($scope.system);
      // Game.setSystem($scope.sysName);
    }


  });
