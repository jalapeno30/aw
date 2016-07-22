'use strict';

angular.module('lotteryApp')
  .controller('AppSettingsCtrl', function ($scope, User) {

    $scope.$watch(function(){
      return User.isAdmin();
    },function(newVals){
      if (newVals !== true) {
        window.location = "/";
      }
    });

    $scope.activeSettings = 'user';


  });
