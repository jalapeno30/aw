'use strict';

angular.module('lotteryApp')
  .controller('TabCtrl', function ($scope, Game, Games) {

	$scope.tabs = Games.getGames();

  $scope.$watch(function(){
    return Games.getGames();
  }, function(newVal){
    $scope.tabs = newVal;
  });

	$scope.navType = 'pills';

	$scope.selectGame = function(numbers) {
		Game.setNumbers(numbers);
		// console.log($scope.tabs);
		for (var i = 0; i < $scope.tabs.length; i++) {
			if ($scope.tabs[i].numbers == parseInt(numbers)) {
				Game.setTotalNumbers($scope.tabs[i].numbers);
				Game.setGameType($scope.tabs[i].title);
				Game.setGameMultiplier($scope.tabs[i].multiplier);
				Game.setGameLogo($scope.tabs[i].logo);
				Game.setAvailableDraws($scope.tabs[i].draws);

				break;
			}
		}
	}
  });
