'use strict';

angular.module('lotteryApp')
  .service('Roles', function Roles($http, ENV, User) {

    var self = this;

    self.roles = [];

    self.getRoles = function() {
      return self.roles;
    };

    self.retrieveRoles = function() {
      $http.get(ENV.apiEndpoint + '/authentication/rolesList')
      .success(function(data){
        self.roles = data;
      })
      .error(function(data){
        console.log(data);
      });
    };

    self.getPermissionsByRole = function(roleId) {
        return $http.get(ENV.apiEndpoint + '/authentication/getRolePermissions', {
            params: {
                roleId: roleId
            }
        });
    };

    self.savePermissions = function(data) {
        return $http.post(ENV.apiEndpoint + '/authentication/savePermissions?token=' + User.getToken(),
            data.data);
    };

    self.retrieveRoles();

  });
