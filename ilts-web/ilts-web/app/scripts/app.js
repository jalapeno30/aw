/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: scripts\app.js
 */
'use strict';

angular.module('lotteryApp', [
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngRoute',
    'ui.bootstrap',
    'LocalStorageModule',
    'config',
    'pascalprecht.translate',
    'ngTable',
    'blockUI',
    'ngBootstrap',
    //'selectize',
    'ui.select2',
    'angular-growl',
    'ngAnimate'
])
.config(function ($routeProvider, $translateProvider, growlProvider, blockUIConfigProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'views/home.html',
            controller: 'HomeCtrl',
            controllerAs: 'home'
        })
//        .when('/play', {
//            templateUrl: 'views/lottoplay.html',
//            controller: 'PlayCtrl',
//            controllerAs: 'play'
//        })
        .when('/play', {
            templateUrl: 'views/bet.html',
            controller: 'BetCtrl',
            controllerAs: 'bet'
        })
        .when('/results', {
            templateUrl: 'views/results.html',
            controller: 'ResultsCtrl'
        })
        .when('/register', {
            templateUrl: 'views/register.html',
            controller: 'RegisterCtrl'
        })
        .when('/how-to-play', {
            templateUrl: 'views/how-to-play.html',
            controller: 'HowToPlayCtrl'
        })
        .when('/faq', {
            templateUrl: 'views/faq.html',
            controller: 'FaqCtrl'
        })
        .when('/about', {
            templateUrl: 'views/about.html',
            controller: 'AboutCtrl'
        })
        .when('/purchases', {
            templateUrl: 'views/purchases.html',
            controller: 'PurchasesCtrl'
        })
        .when('/games', {
            templateUrl: 'views/games.html',
            controller: 'GamesCtrl'
        })
        .when('/user-profile', {
            templateUrl: 'views/user-profile.html',
            controller: 'UserProfileCtrl'
        })
        //it3
        .when('/payment', {
            templateUrl: 'views/payment-screen.html',
            controller: 'PaymentScreenCtrl',
            controllerAs: 'pay'
        }).when('/payment-test', {
            templateUrl: 'views/payment-test.html',
            controller: 'PaymentTestCtrl',
            controllerAs: 'payTest'
        })
        .when('/paypal-callback', {
            templateUrl: 'views/paypal-callback.html',
            controller: 'PaypalCallbackCtrl'
        })
        .when('/paypal-action', {
            templateUrl: 'views/paypal-action.html',
            controller: 'PaypalActionCtrl'
        })
        .when('/paypal-fund-confirm/:id', {
            templateUrl: 'views/paypal-fund-confirm.html',
            controller: 'PaypalFundConfirmCtrl'
        })
        .when('/paypal-fund-cancel/:id', {
            templateUrl: 'views/paypal-fund-cancel.html',
            controller: 'PaypalFundCancelCtrl'
        })
        .when('/app-settings', {
            templateUrl: 'views/app-settings.html',
            controller: 'AppSettingsCtrl'
        })
        .when('/reports', {
            templateUrl: 'views/reports.html',
            controller: 'ReportsCtrl',
            controllerAs: 'reports'
        })
        .when('/reports/tickets', {
            templateUrl: 'views/report-tickets.html',
            controller: 'ReportTicketsCtrl',
            controllerAs: 'tickets'
        })
        .when('/reports/bets', {
            templateUrl: 'views/report-bets.html',
            controller: 'ReportBetsCtrl',
            controllerAs: 'bets'
        })
        .when('/reports/recovery', {
            templateUrl: 'views/recovery-table.html',
            controller: 'RecoveryTableCtrl',
            controllerAs: 'recovery'
        })
        .when('/reports/logs/transaction', {
            templateUrl: 'views/log-transaction.html',
            controller: 'LogTransactionCtrl',
            controllerAs: 'transactions'
        })
        .when('/reports/logs/active-purchase', {
            templateUrl: 'views/log-active-purchase.html',
            controller: 'LogActivePurchaseCtrl',
            controllerAs: 'activePurchases'
        })
        .when('/reports/logs/current-purchase', {
            templateUrl: 'views/log-current-purchase.html',
            controller: 'LogCurrentPurchaseCtrl',
            controllerAs: 'currentPurchases'
        })
        .when('/reports/logs/purchase', {
            templateUrl: 'views/log-purchase.html',
            controller: 'LogPurchaseCtrl',
            controllerAs: 'purchases'
        })
        .otherwise({
            redirectTo: '/'
        });

    $translateProvider.translations('en', {
        "PAGE_HEADINGS": {
            "HOME": "Home",
            "GAMES": "Games",
            "RESULTS": "Results",
            "HOW_TO_PLAY": "How to Play",
            "BUY_LOTTO": "Buy Lotto",
            "ABOUT": "About",
            "FAQ": "FAQ's",
            "PURCHASES": "Purchases",
            "APP_SETTINGS": "Application Settings",
            "REPORTS": "Reports"
        },
        "HOME": {
            "ANNOUNCEMENTS": "Announcements",
            "MORE_ANNOUNCEMENTS": "More Announcements",
            "RESULTS": "Results",
            "WINNERS": "{{result.date}} {{result.winners}} winner(s)"
        },
        "RESULTS": {
            "GAMES": "Games",
            "MORE_RESULTS": "More {{game.gameName}} Results"
        },
        "BUY_LOTTO": {
            "STEPS": {
                "CHOOSE_A_GAME": "Choose a Game",
                "CHOOSE_YOUR_DRAW": "Choose Your Draw",
                "CHOOSE_YOUR_SYSTEM": "Choose Your System",
                "CHOOSE_YOUR_NUMBERS": "Choose Your Numbers"
            },
            "BUTTONS": {
                "RESET": "Reset",
                "ADD_TO_CART": "Add to Cart"
            },
            "BODY": {
                "SELECTED_NUMBERS": "Selected Numbers"
            }
        },
        "SHOPPING_CART": {
            "ORDERS": "Orders",
            "NO_ORDERS": "No orders",
            "REMOVE": "Remove",
            "DRAWS": "Draw",
            "SYSTEM": "System",
            "NUMBERS": "Numbers",
            "BUY_NOW": "Buy Now"
        },
        "PURCHASES": {
            "DETAILS": "Details",
            "GAME": "Game",
            "DRAW": "Draw",
            "SYSTEM": "System",
            "NUMBERS": "Numbers",
            "AMOUNT": "Amount"
        }
    });

    $translateProvider.useLoader('languageLoader');
    $translateProvider.preferredLanguage('en');


    growlProvider.onlyUniqueMessages(false);
    growlProvider.globalTimeToLive(3000);
    blockUIConfigProvider.autoBlock = false;
})
.constant('NAV_BUTTONS', [
    {
        "name" : "PAGE_HEADINGS.HOME",
        "url" : "#/",
        "icon" : "search",
        "restricted": false,
        "admin": false,
        "nonadmin" : false
    },
    {
        "name" : "PAGE_HEADINGS.GAMES",
        "url" : "#/games",
        "icon" : "flag",
        "restricted": false,
        "admin": false,
        "nonadmin" : false
    },
    {
        "name" : "PAGE_HEADINGS.RESULTS",
        "url" : "#/results",
        "icon" : "list",
        "restricted": false,
        "admin": false,
        "nonadmin" : false
    },
    {
        "name" : "PAGE_HEADINGS.HOW_TO_PLAY",
        "url" : "#/how-to-play",
        "icon" : "question-sign",
        "restricted": false,
        "admin": false,
        "nonadmin" : false
    },
    {
        "name" : "PAGE_HEADINGS.BUY_LOTTO",
        "url" : "#/play",
        "icon" : "shopping-cart",
        "restricted": true,
        "admin": false,
        "nonadmin" : true
    },
    {
        "name" : "PAGE_HEADINGS.ABOUT",
        "url" : "#/about",
        "icon" : "info-sign",
        "restricted": false,
        "admin": false,
        "nonadmin" : false
    },
    {
        "name" : "PAGE_HEADINGS.FAQ",
        "url" : "#/faq",
        "icon" : "ok",
        "restricted": false,
        "admin": false,
        "nonadmin" : false
    },
    {
        "name" : "PAGE_HEADINGS.PURCHASES",
        "url" : "#/purchases",
        "icon" : "usd",
        "restricted": true,
        "admin": false,
        "nonadmin" : false
    },
    {
        "name" : "PAGE_HEADINGS.REPORTS",
        "url" : "#/reports",
        "icon" : "file",
        "restricted": true,
        "admin": true,
        "nonadmin" : false
    }
])
.run(function($rootScope, $timeout) {
    $rootScope.UTIL = {
        setTimeout: function(scope, fn, delay) {
            var promise = $timeout(fn, delay);
            var deregister = scope.$on('$destroy', function() {
                $timeout.cancel(promise);
            });

            promise.then(deregister);
        },
        encodeQueryData: function (data) {
            var ret = [];

            for (var d in data) {
                ret.push(encodeURIComponent(d) + "=" + encodeURIComponent(data[d]));
            }

            return ret.join("&");
        }
    };
});