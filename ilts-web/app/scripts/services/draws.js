'use strict';

angular.module('lotteryApp')
  .service('Draws', function Draws($http, ENV, User) {

    var self = this;
    self.draws = [];
    self.statuses = [];

    self.refreshDraws = function() {
      $http.get(ENV.apiEndpoint + '/games/getDraws')
        .success(function(data) {
          if (data.status == 'success') {
            // var draws = [];
            // for (var i = 0; i < data.data.draws.length; i++) {
            //   data.draws[i].date = new Date(data[draws[i].date])
            //   draws.push(data.data.draws[i]);
            // }
            self.draws = data.data.draws;
            for (var i = 0; i < self.draws.length; i++) {
              self.draws[i].date = new Date(self.draws[i].date);
            }
          }
        });
    }

    self.getDraws = function() {
      return self.draws;
    }

    self.refreshStatuses = function() {
      $http.get(ENV.apiEndpoint + '/games/drawsStatusesList')
      .success(function(data) {
        self.statuses = data;
      });
    }

    self.getStatuses = function() {
      return self.statuses;
    }

    self.changeStatus = function(drawId, statusId) {
      $http({
        url: ENV.apiEndpoint + '/games/changeDrawStatus',
        method: 'GET',
        params: {
          "drawId" : drawId,
          "statusId" : statusId
        }
      })
      .success(function(data){
        // console.log(data);
        self.refreshDraws();
      })
      .error(function(data){
        // console.log(data);
      });
    }

    self.createDraw = function(drawObject) {
      $http({
        url: ENV.apiEndpoint + '/games/createDraw?token=' + User.getToken(),
        method: 'POST',
        data: {
          gameId: drawObject.game,
          jackpot: drawObject.jackpot,
          code: drawObject.code,
          date: drawObject.date
        }
      })
      .success(function(data){
        // console.log(data);
        self.refreshDraws();
      });
    }

    self.refreshDraws();
    self.refreshStatuses();

  });
