'use strict';

angular.module('lotteryApp')
  .controller('PlayCtrl', function ($scope, User, blockUI) {

    $scope.$watchCollection(function(){
        return [User.logged, User.name, User.isAdmin()];
      }, function(newvals){
        if ((newvals[0] != undefined && newvals[0] != true && newvals[0] != "true")/* || newvals[2] == true*/) {
            window.location = "/#";
          }
      });


    blockUI.reset();


  });
