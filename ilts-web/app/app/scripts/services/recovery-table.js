'use strict';

angular.module('lotteryApp')
  .service('RecoveryTable', function RecoveryTable(User, ENV, $http) {

        var self = this;
        self.data = [];

        self.getRecoveryData = function(){
            return self.data;
        };

        self.retrieveRecoveryData = function(){
            var url = ENV.apiEndpoint + '/betting/getRecoveryData';
            $http({
                url: url,
                method: 'GET',
                params: {
                    "token": User.getToken()
                }
            })
                .success(function(data, status, headers, config){
                    self.data = data;
                });
        };

        self.retrieveRecoveryData();

        self.retrieveCustomRecoveryData = function(data) {
            var sendData = data;
            sendData.token = User.getToken();
            var url = ENV.apiEndpoint + '/betting/getRecoveryData';
            $http({
                url: url,
                method: 'GET',
                params : sendData
            })
            .success(function(data, status, headers, config){
                self.data = data;
            });
        };

  });
