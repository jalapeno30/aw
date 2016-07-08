'use strict';

angular
	.module('lotteryApp')
	.directive('navButtons', navButtons);

function navButtons(NAV_BUTTONS, User) {
	var directive = {
		restrict: 'E',
		replace: true,
		controller: navButtonsController,
		controllerAs: 'navButtons',
		link: navButtonsLink,
		templateUrl: 'views/directives/nav-buttons.html'
	};

	return directive;

	function navButtonsController($scope) {
		var vm = this;

		vm.logged = User.logged;
		vm.isAdmin = User.isAdmin();

		$scope.$watchCollection(function(){
			return [User.logged, User.name, User.isAdmin()];
		}, function(newvals){
			if (newvals[0] != undefined) {
				vm.logged = newvals[0];
			}
			if (newvals[1] != undefined) {
				vm.username = newvals[1];
			}
			vm.isAdmin = newvals[2];
		});


		vm.menus = NAV_BUTTONS;

		vm.updateHeading = function(heading) {
			// Main.setHeading(heading);
		}

	}

	function navButtonsLink(scope, elem, attr, ctrl) {

	}
}