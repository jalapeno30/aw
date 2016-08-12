/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * it3
 */

'use strict';

angular.module('lotteryApp')
  .controller('PaymentScreenCtrl', PaymentScreenCtrl);

function PaymentScreenCtrl($scope, $location, User, Payment, Orders, growl) {
    
    var self=this;
    self.orderIds = Orders.getOrderIds();
    self.token = User.getToken();
    self.userId = User.id;
    self.transId; //unique id assigned to transaction
    self.status; //to update status as order progresses
    self.tabSelection=0; //start at tab 0
    self.cardSwitch; //card type
    self.newCard; // new card data to be submitted 
    self.existingCardSelected; // selected existing card
    
    
//    tab names and id. id to help with ng-class
    self.tabs = [{tabName: "Order Review", tabId : 0},{tabName: "Card Selection", tabId : 1},{tabName: "Confirmation", tabId : 2},{tabName: "Success", tabId : 3}];   
    //determine tab class based on tab id above
    self.tabClass=function(index){
        if(index > self.tabSelection){
            return "btn-default"
        }else if(index == self.tabSelection){
            return index == 3 ? "btn-default": "card-selected"
        }else return "btn-success"
    };   
    // same as above but determines if a tab can be clicked
    self.tabIsActive = function (index){
        if(self.tabSelection != 4){
            return index >= self.tabSelection ? true : false;
        }else{
            return true;
        }
      
    };
    //change tab on click (instead of using back/comtinue button)
    self.tabClick= function(index){
        self.tabSelection=index;
    }; 
    //determine which directive to display based on tab selection
    self.tabContent=function(index){
        return index === self.tabSelection ? true : false;
    };   
//--------- end tab functionality

//----Card stuff    
    self.cardToggle = function(selection){
        self.cardSwitch = selection;
    };
    //choosing from existing cards
    self.cardSelect = function(cardSelection){
        if(cardSelection == self.existingCardSelected){
            //if the user clicks on an existing card, clear selection (toggle on/off)
            self.existingCardSelected = '';
        }else{
            //else set chosen card to card selection
            self.existingCardSelected = cardSelection;
        }
    };
      
//--------start bottom button funxtionality  
    self.continue = function(){
        switch(self.tabSelection) {
            case 0: //order review 
                //get transId from payment service (orig set from shopping cart buy now)
                self.transId=Payment.transId;
                self.tabSelection++;
                break;
            case 1: //submit card
                //if new card
                if(self.cardSwitch === 'new'){
                    //capture inputs and fire Payment.newCardDetails
                    Payment.newCardDetails(self.token, self.newCard, self.transId, self.userId).then(function(response){
                        if(response.data.customMessage == 'Processing'){
                        //this is a success - continue
                        Payment.setCard(self.cardSwitch, self.newCard);
                        self.tabSelection++;
                    }else{
                        //this is a failure, alert message if available else alert generic error
                        bootbox.alert( response.data ? response.data.customMessage : 'Error Processing Card')
                    }
                    });
                    break;
                }else{
                    //if existing card
                    //capture selected cardId and fire Payment.existingCardDetails
                    if(self.existingCardSelected){
                        Payment.continueExistingCard(self.token, self.existingCardSelected.cardNumber, self.transId, self.userId).then(function(response){
                            if(response.status == 200){
                               //if success contiue 
                               Payment.setCard(self.cardSwitch, self.existingCardSelected);
                                self.tabSelection++;                           
                            }else{
                                //else display error message
                                bootbox.alert( response.data ? response.data.customMessage : 'Error Processing Card')
                            };                        
                        });
                    }else {
                        growl.addErrorMessage('Choose a card');
                    }
                    break; 
                };
            case 2: //final order and payment review
                //update status //if success contiue //else display error message
                Payment.updateStatus(self.token, self.userId, self.transId,"processing").then(function(response){
                    if(response.data.customMessage == "Processing"){
                        self.tabSelection++;
                    }else{
                         bootbox.alert( response.data ? response.data.customMessage : 'Error')
                    }
                });                                              
                break;
            case 3: //buy now + process card
                //last status //if success contiue //else display error message
                Payment.updateStatus(self.token, self.userId, self.transId,"confirming").then(function(response){
                    if(response.data.customMessage == "Processing"){
                        self.tabSelection++;
                    }else{
                         bootbox.alert( response.data ? response.data.customMessage : 'Error')
                    }
                });                                              
                break;           
        }   
    };   
    self.back = function(){
        self.tabSelection--;
    };  
    self.exit = function(){
        //confirm exit before closing, unless we are on final tab
        if(self.tabSelection !== 4){
            bootbox.confirm("Are you sure you want to leave this page?", function(result) {
                if(result == true){
                    Payment.updateStatus(self.token, self.userId, self.transId,"deleted");
                    $location.path("/play");
                }
              }); 

        }else{
            //on final tab clear orders before leaving
            Orders.removeAllOrders(self.orderIds);
            $location.path("/play");
        }        
    };
//---------end bottom button functionality
};