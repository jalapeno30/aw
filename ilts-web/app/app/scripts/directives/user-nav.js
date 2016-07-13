'use strict';

angular
    .module('lotteryApp')
    .directive('userNav', userNav)
    .directive('userNavList', userNavList)
    .directive('userLanguageList', userLanguageList);

function userNav(User, $modal) {
    var directive = {
        templateUrl: 'views/directives/user-nav.html',
        restrict: 'E',
        replace: true,
        scope: {},
        controller: userNavController,
        controllerAs: 'userNav',
        link: userNavLink,
    };

    return directive;

    function userNavController($scope) {
        var vm = this;
    }

    function userNavLink(scope, elem, attr, ctrl) {

    }
}

function userNavList(User, $modal) {
    var directive = {
        templateUrl: 'views/directives/user-nav-list.html',
        restrict: 'E',
        replace: true,
        scope: {},
        controller: userNavListController,
        controllerAs: 'userNavList',
        link: userNavListLink,
    };

    return directive;

    function userNavListController($scope) {
        var vm = this;

        vm.logged = User.logged;
        vm.username = "";
        vm.isAdmin = User.isAdmin;

        $scope.$watchCollection(function(){
            return [User.logged, User.name, User.isAdmin()];
        }, function(newvals){
            if (newvals[0] != undefined) {
                vm.logged = newvals[0];
            }
            if (newvals[1] != undefined) {
                vm.username = newvals[1];
            }
            if (newvals[2] != undefined) {
                vm.isAdmin = newvals[2];
            }
        }, true);

        vm.login = function() {
            var modalInstance = $modal.open({
                templateUrl: 'views/partials/loginModal.html',
                controller: 'ModalInstanceCtrl'
            });

            modalInstance.result.then(function(user){
                User.logIn(user.name, user.password);
            });
        }

        vm.logout = function() {
            User.logout();
        }

    }

    function userNavListLink(scope, elem, attr, ctrl) {
    }
}


function userLanguageList(Languages, $translate) {
    var directive = {
        templateUrl: 'views/directives/user-language-list.html',
        restrict: 'E',
        replace: true,
        scope: {},
        controller: userLanguageListController,
        controllerAs: 'userLanguageList',
        link: userLanguageListLink,
    };

    return directive;

    function userLanguageListController($scope) {
        var vm = this;

        vm.languages;
        $scope.$watch(function(){
            return Languages.getLanguagesShort();
        }, function(newVal){
            vm.languages = newVal;
        }, true);

        vm.language = {
            'selected': $translate.use()
        }

        vm.updateLanguage = function(lang){

            Languages.use(vm.language.selected);
        };

    }

    function userLanguageListLink(scope, elem, attr, ctrl) {

    }
}
