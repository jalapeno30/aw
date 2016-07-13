'use strict';

angular.module('lotteryApp')
  .service('Users', function Users($http, ENV) {

    var self = this;

    self.users = [];
    self.statuses = [];
    self.roles = [];

    self.refreshUsers = function() {
      $http.get(ENV.apiEndpoint + '/authentication/usersList')
      .success(function(data){
        self.users = [];
        for (var i = 0; i < data.length; i++) {
          self.users.push({
            "id" : data[i].userId,
            "username" : data[i].userName,
            "role" : data[i].role,
            "status" : data[i].status
          });
        }
      })
      .error(function(data){
        console.log(data);
      });
    };

    self.refreshStatusList = function() {
      $http.get(ENV.apiEndpoint + '/authentication/statusList')
      .success(function(data){
        self.statuses = data;
      })
      .error(function(data){
        console.log(data);
      });
    }

    self.refreshRolesList = function() {
      $http.get(ENV.apiEndpoint + '/authentication/rolesList')
      .success(function(data){
        self.roles = data;
      })
      .error(function(data){
        console.log(data);
      });
    }

    self.getUsers = function() {
      return self.users;
    }

    self.getStatuses = function() {
      return self.statuses;
    }

    self.getRoles = function() {
      return self.roles;
    }

    self.changeStatus = function(id, status) {
      $http({
        url: ENV.apiEndpoint + '/authentication/changeStatus',
        method: 'GET',
        params: {
          "userId" : id,
          "statusId" : status
        }
      })
      .success(function(data){
        // console.log(data);
        self.refreshUsers();
      })
      .error(function(data){
        // console.log(data);
      });
    }

    self.changeRole = function(id, role) {
      $http({
        url: ENV.apiEndpoint + '/authentication/changeRole',
        method: 'GET',
        params: {
          "userId" : id,
          "roleId" : role
        }
      })
      .success(function(data){
        // console.log(data);
        self.refreshUsers();
      })
      .error(function(data){
        // console.log(data);
      });
    }

    self.refreshUsers();
    self.refreshStatusList();
    self.refreshRolesList();

  });
