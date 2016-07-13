/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * it3
 */


'use strict';

angular.module('lotteryApp')
  .controller('PaymentScreenCtrl', function ($scope) {
      
      var self=this;
      self.false=false;
      self.tabSelection=0;
      
      self.tabs = [{tabName: "Order Review", tabId : 0},{tabName: "Card Selection", tabId : 1},{tabName: "Confirmation", tabId : 2},{tabName: "Success", tabId : 3}];
      
      self.tabClass=function(index){
          if (index == self.tabSelection){
              return "danger";
          }
      };
      self.tabClick= function(index){
          self.tabSelection=index;
      };
      
      self.tabContent=function(index){
          return index === self.tabSelection ? true : false;
      };
      
      $scope.$watch(function () {
          return self.tabSelection;
      },function(value){
           alert(value)
      });           
  });