'use strict';

angular
  .module('lotteryApp')
  .directive('cardSelection', cardSelection);

function cardSelection(User, Payment) {
    var directive = {
        restrict: 'E',
        controller: cardSelectionController,
        controllerAs: 'cardSelection',
        link: cardSelection,
        templateUrl: 'views/directives/card-selection.html'
    };
    return directive;

    function cardSelectionController() {
        var self = this;
        self.token = User.getToken();
        self.userId = User.id;
        self.existingCards;
        self.buttonSwitch;
        self.class;

        //switch between bew and existing card displays
        self.click=function(string){
            self.buttonSwitch = string;
            if(string == 'existing'){
                self.getCards();   
           }
        };    

        //highlight the selected existing card, unhighlight on 2 clicks
        self.classToggle = function (input){
            self.class = (self.class == input ? '' : input);
        };

        //applies classToggle functionality with ng-class
        self.classSwitch = function (input){
            if(self.class == input){
                return 'card-selected';
            };
        };

        //comment
        self.getCards = function (){
            Payment.existingCardDetails(self.token, self.userId).then(function(response){
                self.existingCards = response.data;
            });             
        };

        //comment
        self.deleteCard = function (cardId){
            bootbox.prompt("Type delete to confirm card deletion:", function(result) {                
                if (result == 'delete') {
                    Payment.deleteCard(self.token, self.userId, cardId).then(function(response){
                        if(response.data){
                            bootbox.alert("This will be a success when back-end is ready")
                        }else{
                            bootbox.alert("error");
                        };
                    });  
                }
            });           
        };  

        //months and years for card selection
        self.months = [1,2,3,4,5,6,7,8,9,10,11,12]
        self.years = function(){
            var yearRange = [];
            var currentYear = new Date().getFullYear()

            for (var i = currentYear; i <= currentYear + 20; i ++){
                 yearRange.push(i);
            }
            return yearRange;
        };
        
        //Country will come from the backend eventualy
        self.country = function(){
            return ['United States']
        };
        //State will come from the backend eventually
        self.states = function(){
            return ["AK","AL","AR","AZ","CA","CO","CT","DC","DE","FL","GA","GU","HI","IA","ID", "IL","IN","KS","KY","LA","MA","MD","ME","MH","MI","MN","MO","MS","MT","NC","ND","NE","NH","NJ","NM","NV","NY", "OH","OK","OR","PA","PR","PW","RI","SC","SD","TN","TX","UT","VA","VI","VT","WA","WI","WV","WY"]
        };
        
    };
}

