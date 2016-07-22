'use strict';

angular.module('lotteryApp')
  .controller('AppDrawsCtrl', function ($scope, $filter, Draws, Games, ngTableParams) {

    var self = this;

    self.draws = Draws.getDraws();
    self.statuses = Draws.getStatuses();
    self.activeCreate = false;
    self.newDraw = {};
    self.createFormSubmitted = false;
    self.games = Games.getGames();
    self.datepicker = {
      format : 'yyyy/MM/dd',
      opened: false,
      minDate: new Date()
    }

    self.dateOpen = function($event) {
      $event.preventDefault();
      $event.stopPropagation();

      self.datepicker.opened = true;
    }

    $scope.$watch(function(){
      return Games.getGames();
    }, function(newVals){
      self.games = newVals;
    });

    $scope.$watchCollection(function(){
      return [Draws.getDraws(), Draws.getStatuses()];
    }, function(newVal){
      self.draws = newVal[0];
      self.statuses = newVal[1];
      self.tableParams.reload();
    });

    self.changeStatus = function(drawId, statusId) {
      var statConfirm = window.confirm("Are you sure you want to change the status?");
      if (statConfirm) {
        Draws.changeStatus(drawId, statusId);
      }
    }

    self.toggleCreateForm = function() {
      self.activeCreate = !self.activeCreate;
    }

    self.cancelCreateForm = function() {
      self.newDraw = {};
      self.toggleCreateForm();
    }

    self.submitCreateDrawForm = function(isValid) {
      self.createFormSubmitted = true;
      if (isValid) {
        Draws.createDraw(self.newDraw);
        self.toggleCreateForm();
        self.createFormSubmitted = false;
      }
    }

    self.tableParams = new ngTableParams({
      page: 1,
      count: 10,
      sorting: {
        id: 'asc'
      }
    }, {
      total: 0,
      getData: function($defer, params) {
        var orderedData = params.sorting() ? $filter('orderBy')(self.draws, params.orderBy()) : self.draws;
        params.total(self.draws.length);
        $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
      }
    });


  });
