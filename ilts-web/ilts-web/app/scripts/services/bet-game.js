'use strict';

angular.module('lotteryApp')
  .factory('betGame', function (ENV, $http) {

        var betGame = function() {
            var game = {};
            var draws = [];
            var system = 6;

            return {
                initialize: function(data) {
                    game = data;
                },
                getGame: function() {
                    return game;
                },
                getDraws: function() {
                    return draws;
                },
                retrieveDraws: function() {
                    var url = ENV.apiEndpoint + "/games/" + game.id + "/draws/available";
                    $http({
                        url: url,
                        method: "GET"
                    })
                        .success(function(data) {
                            draws = data;
                        });
                },
                getSystem: function() {
                    return system;
                },
                setSystem: function(sys) {
                    system = sys;
                }
            }
        };
        return betGame;
  });
