'use strict';

angular.module('lotteryApp')
  .service('Languages', function Languages($http, ENV, User, $translate) {

    var self = this;
    self.languages = [];
    self.languagesShort = [];

    self.retrieveLanguages = function() {
      $http.get(ENV.apiEndpoint + '/settings/languagesSerialized')
        .success(function(data){
          var lang = data;
          var shortLang = [];
          for (var i = 0; i < data.length; i++) {
            lang[i].json = JSON.parse(data[i].json);
            shortLang.push({
              id: data[i].alias,
              text: data[i].name
            })
          }
          self.languages = lang;
          self.languagesShort = shortLang;
        });
    };

    self.getLanguages = function() {
      return self.languages;
    }

    self.getLanguagesShort = function() {
      return self.languagesShort;
    }

    self.getLanguage = function(alias) {
      $http.get(ENV.apiEndpoint + '/settings/languagesSerialized/' + alias)
        .success(function(data){
          // deferred.resolve(JSON.parse(data.json));
          console.log(alias, JSON.parse(data.json));
          $translate.translations(alias, JSON.parse(data.json));
        });
    }

    self.saveLanguage = function(details, object) {
      var url = ENV.apiEndpoint + '/settings/saveLanguagesSerialized?token=' + User.getToken();
      var data = {
        id: details.id,
        json: JSON.stringify(object)
      };
      return $http.post(url, data);
    }

    self.saveNewLanguage = function(details, object) {
      var url = ENV.apiEndpoint + '/settings/newLanguagesSerialized?token=' + User.getToken();
      var data = {
        name: details.name,
        alias: details.alias,
        json: JSON.stringify(object)
      };
      return $http.post(url, data);
    }

    self.use = function(alias) {
      $translate.refresh(alias);
      $translate.use(alias);
    }

    self.retrieveLanguages();

  });
