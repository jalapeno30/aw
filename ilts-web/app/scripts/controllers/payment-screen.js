/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * it3
 */

'use strict';

angular.module('lotteryApp')
  .controller('PaymentScreenCtrl', PaymentScreenCtrl);

function PaymentScreenCtrl($location, User, Payment, Orders) {
    
    var self=this;
    self.false=false;
    self.tabSelection=0;   
    self.newCard; // new card data to be submitted 
    self.existingCards;
    self.existingCardSelected;
    
//    tab names and id. id to help with ng-class
    self.tabs = [{tabName: "Order Review", tabId : 0},{tabName: "Card Selection", tabId : 1},{tabName: "Confirmation", tabId : 2},{tabName: "Success", tabId : 3}];
    
    //determine tab class based on tab id above
    self.tabClass=function(index){
        if(index > self.tabSelection){
            return "btn-default"
        }else if(index == self.tabSelection){
            return index == 3 ? "btn-default": "active"
        }else return "btn-success"
    };
    // same as above but determines if a tab can be clicked
    self.tabIsActive = function (index){
      return index >= self.tabSelection ? true : false;
    };
    
    //change tab on click (instead of using back/comtinue button)
    self.tabClick= function(index){
        self.tabSelection=index;
    };
    
    //determine which directive to display based on tab selection
    self.tabContent=function(index){
        return index === self.tabSelection ? true : false;
    };
      
    //start bottom button funxtionality  
    self.continue = function(){
        self.tabSelection++;
    };   
    self.back = function(){
        self.tabSelection--;
    };  
    self.exit = function(){
        $location.path("/play");
    }
    //end bottom button functionality

  };