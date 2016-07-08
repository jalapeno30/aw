/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: services\user.ja
 */

'use strict';

angular.module('lotteryApp')
    .service('User', function User($http, ENV, localStorageService, growl) {
        // AngularJS will instantiate a singleton by calling "new" on this function

        var self = this;

        self.logged = (localStorageService.get('logged') === null) ? false : localStorageService.get('logged');
        self.name = (localStorageService.get('username') === null) ? "" : localStorageService.get('username');

        self.isAdmin = function () {
            return (localStorageService.get('role') === "Administrator");
        };

        // console.log(localStorageService.get('username'));

        self.logIn = function (username, password) {
            $http.get(ENV.apiEndpoint + '/authentication/userLogin', {
                params: {
                    username: username,
                    password: password
                }
            })
            .success(function (data) {
                if (data.status === "success") {
                    self.logged = true;
                    self.name = username;

                    localStorageService.remove('logged');
                    localStorageService.set('logged', true);
                    localStorageService.remove('sessionToken');
                    localStorageService.set('sessionToken', data.token);
                    localStorageService.remove('username');
                    localStorageService.set('username', data.user_name);
                    localStorageService.remove('role');
                    localStorageService.set('role', data.role);
                    growl.addSuccessMessage('You have successfully logged in.');

                    //      growl.addSuccessMessage(data.message);

                    window.location = "#";
                } else {   // data.status === "error"
                    if (data.message !== null) {
                        growl.addErrorMessage('Invalid: ' + data.message);
                    }
                }

                // console.log(data);
            })
            .error(function (data) {
                if ((data !== null) && (data.message !== null)) {
                    growl.addErrorMessage(data.message);
                } else {
                    growl.addErrorMessage('User Log in failed: Unknown Error');
                }
                // console.log(data);
            });
        };

        self.logout = function () {
            localStorageService.remove('logged');
            localStorageService.remove('sessionToken');
            localStorageService.remove('username');
            localStorageService.remove('role');

            self.logged = false;
            growl.addSuccessMessage('You have successfully logged out.');
        };

        self.getToken = function () {
            return (localStorageService.get('sessionToken') === null) ? false : localStorageService.get('sessionToken');
        };

        self.register = function (data) {
            var formattedData = data;

            $http.post(ENV.apiEndpoint + '/authentication/register', formattedData)
                .success(function (data) {
                    if (data.status === "success") {
                        if (data.message !== null) {
                            growl.addSuccessMessage(data.message);
                        }

                        window.location = "#";
                    } else {   // data.status === "error"
                        if (data.message !== null) {
                            var warnMsg = "";
                            var tokens = data.message.split(":");
                            var dspMsg = 'User Registration: ' + tokens[0];
                            growl.addErrorMessage(dspMsg);

                            if (tokens[1] === 'user_name') {
                                warnMsg = "Username";

                                var obj = document.getElementById("alert_register_" + tokens[1]);
                                obj.innerHTML = "<i class=\"glyphicon glyphicon-exclamation-sign\"></i>Duplicate entry.";
                                registerForm.register_user_name.attributes.errmsg.nodeValue = "true";
                            }
                            if (tokens[1] === 'email') {
                                warnMsg = "Email";

                                var obj = document.getElementById("alert_register_" + tokens[1]);
                                obj.innerHTML = "<i class=\"glyphicon glyphicon-exclamation-sign\"></i>Duplicate entry.";
                                registerForm.register_email_id.attributes.errmsg.nodeValue = "true";
                            }
//                            alert("Duplicate entry value in field '" + warnMsg + "'");
                        }
                    }
                })
                .error(function (data) {
                    if ((data !== null) && (data.message !== null)) {
                        growl.addErrorMessage(data.message);
                    } else {
                        growl.addErrorMessage('User Registration: Unknown Error');
                    }

                    // console.log(data);
                });
        };
    });

angular.module('lotteryApp')
    .service('ForgotPassModalInstance', function ForgotPassModalInstance($http, ENV, localStorageService, growl) {
        // AngularJS will instantiate a singleton by calling "new" on this function

        var self = this;

        self.sendForgotPassword = function (scopeObj, username, email) {
            $http.get(ENV.apiEndpoint + '/authentication/forgotPassword', {
                params: {
                    username: username,
                    email: email
                }
            })
            .success(function (data) {
                if (data.status === "success") {
                    if (data.message !== null) {
                        growl.addSuccessMessage(data.message);
                    }

                    scopeObj.close();

                    window.location = "#";
                } else {   // data.status === "error"
                    if (data.message !== null) {
                        growl.addErrorMessage('Forgot Password: ' + data.message);
                    }
                }
            })
            .error(function (data) {
                if ((data !== null) && (data.message !== null)) {
                    growl.addErrorMessage(data.message);
                } else {
                    growl.addErrorMessage('Forgot Password: Unknown Error');
                }

                // console.log(data);
            });
        };
    });
