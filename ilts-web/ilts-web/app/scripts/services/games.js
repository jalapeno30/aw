'use strict';

angular.module('lotteryApp')
  .service('Games', function Games($http, ENV) {

    var self = this;
   	self.games = [];
    self.systems = [];

    self.refreshGames = function() {
    	var url = ENV.apiEndpoint + "/games";
        $http({
            url: url,
            method: 'GET'
        })
            .success(function(data){
                self.games = data;
            })
    };

    self.getGames = function() {
    	return self.games;
    }

    self.refreshGames();

    self.getSystems = function() {
        return self.systems;
    }

    self.refreshSystems = function() {
        var url = ENV.apiEndpoint + "/games/systems";
        $http({
            url: url,
            method: 'GET'
        })
            .success(function(data){
                self.systems = data;
            })
    }

  });
