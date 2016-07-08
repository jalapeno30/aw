'use strict';

angular.module('lotteryApp')
  .factory('languageLoader', function ($http, $q, ENV) {

    // Public API here
    return function(options){
      var deferred = $q.defer();

      $http.get(ENV.apiEndpoint + '/settings/languagesSerialized/' + options.key)
        .success(function(data){
          deferred.resolve(JSON.parse(data.json));
        });

      return deferred.promise;
    };
  });
