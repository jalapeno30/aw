'use strict';

angular.module('lotteryApp')
  .controller('RolesCtrl', function ($scope, Roles, $filter, ngTableParams, growl) {

    var self = this;
    self.roles = Roles.getRoles();
    self.editable = false;
    self.editForm = {
        "role" : "",
        "data" : []
    };

    $scope.$watch(function(){
      return Roles.getRoles();
    }, function(newVal){
      self.roles = newVal;
      self.tableParams.reload();
    });

    self.tableParams = new ngTableParams({
      page: 1,
      count: 10,
      sorting: {
        id: 'asc'
      }
    }, {
      total: 0,
      getData: function($defer, params) {
        var orderedData = params.sorting() ? $filter('orderBy')(self.roles, params.orderBy()) : self.roles;
        params.total(self.roles.length);
        $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
      }
    });

    self.editPermissions = function(roleId) {
      Roles.getPermissionsByRole(roleId)
          .success(function(data){
              var roleName = getRoleById(roleId).name;
              self.editable = true;
              self.editForm.role = roleName;
              self.editForm.data = data;
              //console.log(data);
          })
          .error(function(data, status, headers, config){

          });
    };

    self.savePermissions = function() {
        Roles.savePermissions(self.editForm)
            .success(function(data){
                growl.addSuccessMessage("Successfully saved permission changes.");
            })
            .error(function(data, status, headers, config){
                growl.addErrorMessage("Error saving permission changes.");
            });
    };

    function getRoleById(roleId) {
        for(var i in self.roles) {
            if (self.roles[i].id == roleId) {
                return self.roles[i];
            }
            if (i == self.roles.length - 1) {
                return null;
            }
        }
    }
  });
