'use strict';

angular.module('lotteryApp')
  .controller('UsersCtrl', function ($scope, $filter, Users, ngTableParams, growl) {

    var self = this;
    self.users = Users.getUsers();
    self.statuses = Users.getStatuses();
    self.roles = Users.getRoles();


    $scope.$watchCollection(function(){
      return [Users.getUsers(), Users.getStatuses(), Users.getRoles()];
    }, function(newVals){
      self.users = newVals[0];
      self.statuses = newVals[1];
      self.roles = newVals[2];
      self.tableParams.reload();
    });


    self.changeStatus = function(id, status) {
      Users.changeStatus(id, status);
      growl.addSuccessMessage('Successfully changed user status.');
    }

    self.changeRole = function(id, role) {
      Users.changeRole(id, role);
    }

    self.tableParams = new ngTableParams({
      page: 1,
      count: 10,
      sorting: {
        id: 'asc'
      }
    }, {
      total: 0,
      getData: function($defer, params) {
        var orderedData = params.sorting() ? $filter('orderBy')(self.users, params.orderBy()) : self.users;
        params.total(self.users.length);
        $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
      }
    });

  });
