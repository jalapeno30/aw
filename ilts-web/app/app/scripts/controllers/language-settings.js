'use strict';

angular.module('lotteryApp')
  .controller('LanguageSettingsCtrl', function ($scope, $filter, Languages, ngTableParams, growl, $translate) {

    var self = this;

    // retrieve languages from backend
    self.languages = Languages.getLanguages();
    $scope.$watch(function(){
      return Languages.getLanguages();
    }, function(newVal){
      self.languages = newVal;
    })

    // toggle for edit form
    self.editable = false;
    self.activeLanguage = {};
    self.activeLanguageDetails = {};
    self.enableSave = true;
    self.newLanguage = false;

    self.tableParams = new ngTableParams({
      page: 1,
      count: 10,
      sorting: {
        name: 'asc'
      }
    }, {
      total: 0,
      getData: function($defer, params) {
        var orderedData = params.sorting() ? $filter('orderBy')(self.languages, params.orderBy()) : self.languages;
        params.total(self.languages.length);
        $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
      }
    });

    self.editLanguage = function(id) {
      self.newLanguage = false;
      self.editable = true;
      for (var i = 0; i < self.languages.length; i++) {
        if (self.languages[i].id == id) {
          self.activeLanguage = self.languages[i].json;
          self.activeLanguageDetails = {
            id: self.languages[i].id,
            name: self.languages[i].name,
            alias: self.languages[i].alias
          }
          break;
        }
      }
    }

    self.hasChild = function(obj) {
      return typeof obj === 'object';
    }

    self.saveLanguage = function() {
      // console.log(self.activeLanguage);

      // disable button
      self.enableSave = false;

      // Backend call to save method
      if (self.newLanguage) {
        var promise = Languages.saveNewLanguage(self.activeLanguageDetails, self.activeLanguage);
      } else {
        var promise = Languages.saveLanguage(self.activeLanguageDetails, self.activeLanguage);
      }

      // notify
      promise
        .success(function(data){
          growl.addSuccessMessage('Successfully saved language changes');
          self.enableSave = true;
          $translate.refresh(self.activeLanguage.alias);
        })
        .error(function(data){
          growl.addErrorMessage('Error saving language changes');
          self.enableSave = true;
        });
    }

    self.createLanguage = function() {
      self.editable = true;
      self.newLanguage = true;
      self.activeLanguage = self.languages[0].json;
      self.activeLanguageDetails = {
        name: "",
        alias: ""
      }
    }

  });
