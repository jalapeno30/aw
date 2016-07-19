/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * it3
 */


'use strict';

angular.module('lotteryApp')
  .controller('PaymentTestCtrl', function (User, Payment) {
      
      var self=this;
      self.token;
      self.username;
      self.status = 0; 
      self.step0Out;
      self.step0Back;
      self.step1Out;
      self.step1Back;
      self.step2Out;
      self.step2Back;
      self.step3Out;
      self.step3Back;   
      self.step4Out;
      self.step4Back;   
      
      self.step0 = function (){
        self.token = User.getToken();
        self.username = User.name;
        
        Payment.newCheckout(self.token, self.username, self.status).then(function(response){
            return response.data;
        }, function(response){
            console.log(response.data);
        });  
        
        self.step0Out = self.token.concat(self.username, self.status);       
      };
      
      

  });