/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: controllers\user.ja
 */

'use strict';

angular.module('lotteryApp')
.controller('UserCtrl', function ($scope, $modal, User) {
    $scope.logged = User.logged;
    $scope.username = "";
    $scope.isAdmin = User.isAdmin;

    $scope.username_error = false;
    $scope.password_error = false;

    $scope.$watchCollection(function() {
        return [User.logged, User.name, User.isAdmin()];
    }, function(newvals){
        if (newvals[0] !== undefined) {
            $scope.logged = newvals[0];
        }
        if (newvals[1] !== undefined) {
            $scope.username = newvals[1];
        }
        if (newvals[2] !== undefined) {
            $scope.isAdmin = newvals[2];
        }
    }, true);

    $scope.login = function() {
        // $scope.logged = true;
        // $scope.username = 'Nicolai';
        var modalInstance = $modal.open({
            templateUrl: 'views/partials/loginModal.html',
            controller: 'ModalInstanceCtrl'
        });

        modalInstance.result.then(function(user){
            // $scope.username = user.name;
            // $scope.logged = true;
            User.logIn(user.name, user.password);
        });
    };

    $scope.logout = function() {
        // $scope.logged = false;
        // $scope.username = '';
        User.logout();
    };
});

angular.module('lotteryApp')
.controller('ModalInstanceCtrl', function ($scope, $modalInstance, $modal) {
    $scope.user = {
        name: '',
        password: ''
    };

    $scope.validate = function(user) {
        var bOk = true;

        if ((user.name.length === 0) || (user.password.length === 0)) {
            if (user.name.length === 0) {
                $scope.username_error = true;
            }

            if (user.password.length === 0) {
                $scope.password_error = true;
            }

            bOk = false;
        }

        if (bOk === true) {
            if (user.name.length > 20) {
                $scope.username_error = true;
                var obj = document.getElementById("username_error");
                obj.innerHTML = "<i class=\"glyphicon glyphicon-exclamation-sign\"></i>Exceed maximum length of 20.";

                bOk = false;
            }
        }

        if (bOk === true) {
            if (user.password.length > 25) {
                $scope.password_error = true;
                var obj = document.getElementById("password_error");
                obj.innerHTML = "<i class=\"glyphicon glyphicon-exclamation-sign\"></i>Exceed maximum length of 25.";

                bOk = false;
            }
        }

        return bOk;
    };

    $scope.loginOk = function() {
        if ($scope.validate($scope.user) === true) {
            $modalInstance.close($scope.user);
        }
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };

    $scope.submitLoginForm = function() {
        $scope.loginOk();
    };

    $scope.requestForgotPassword = function() {
        $scope.cancel();

        var modalInst = $modal.open({
            templateUrl: 'views/partials/forgotpassModal.html',
            controller: 'ForgotPassModalInstanceCtrl'
        });
    };
});

angular.module('lotteryApp')
.controller('ForgotPassModalInstanceCtrl', function ($scope, $modalInstance, ForgotPassModalInstance) {
    $scope.username_error = false;
    $scope.email_error = false;

    $scope.validate = function() {
        var bOk = true;

        if ((forgotpassModal.username.value.length === 0) || (forgotpassModal.email.value.length === 0)) {
            if (forgotpassModal.username.value.length === 0) {
                $scope.username_error = true;
            }

            if (forgotpassModal.email.value.length === 0) {
                $scope.email_error = true;
            }

            bOk = false;
        }

        if (bOk === true) {
            if (forgotpassModal.username.value.length > 20) {
                $scope.username_error = true;
                var obj = document.getElementById("username_error");
                obj.innerHTML = "<i class=\"glyphicon glyphicon-exclamation-sign\"></i>Exceed maximum length of 20.";

                bOk = false;
            }
        }

        if (bOk === true) {
            if (forgotpassModal.email.value.length > 50) {
                $scope.email_error = true;
                var obj = document.getElementById("email_error");
                obj.innerHTML = "<i class=\"glyphicon glyphicon-exclamation-sign\"></i>Exceed maximum length of 50.";

                bOk = false;
            }
        }

        if (bOk === true) {
            var emailTest = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;

            if (forgotpassModal.email.value.match(emailTest) === null) {
                $scope.email_error = true;
                var obj = document.getElementById("email_error");
                obj.innerHTML = "<i class=\"glyphicon glyphicon-exclamation-sign\"></i>Incorrect Email format (Ex: name@somename.com, etc.).";

                bOk = false;
            }
        }

        return bOk;
    };

    $scope.sendForgotPassword = function(username, email) {
        $scope.username_error = false;
        $scope.email_error = false;

        if ($scope.validate() == true) {
            ForgotPassModalInstance.sendForgotPassword($scope, username, email);
        }
    };

    $scope.close = function () {
        $modalInstance.dismiss('close');
    };
});