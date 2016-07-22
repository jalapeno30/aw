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
          if(index > self.tabSelection){
              return "btn-default"
          }else if(index == self.tabSelection){
              return "active"
          }else return "btn-success"
      };
      
      self.tabClick= function(index){
          self.tabSelection=index;
      };
      
      self.tabContent=function(index){
          return index === self.tabSelection ? true : false;
      };
      
      self.arrowClick = function(operator){
          if(operator === 1 && self.tabSelection < self.tabs.length ){
              self.tabSelection ++;
          }else if(operator === 0 && self.tabSelection > 0){
              self.tabSelection --;
          }
      };
          
  });