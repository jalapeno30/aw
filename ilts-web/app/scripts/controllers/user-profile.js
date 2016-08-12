'use strict';

angular.module('lotteryApp')
.controller('UserProfileCtrl', function (User, Users, $scope) {
    var self = this;
    //temp until backend communication is live
    self.dummyData;
    //currently selected user
    self.currentUser;
    //data for currently logged in user (need a real function here)
    self.loggedUser;
    //is user admin (this will need to switch) controls search bar display
    self.loggedAdmin = true;
    
    //set the current user with this function
    self.userSet = function (selectedUser){        
        //if the function is called with param (selected from dropdown)^
        if(selectedUser){
            for(var i = 0; i < self.dummyData.length; i++){
                if(self.dummyData[i].user_name == selectedUser){
                    self.currentUser = self.dummyData[i];
                }
            }
        }else{
            //set to logged in user (we will need a real function here)
            self.currentUser = Users.getUsersTest("token")[0]; 
            //self.currentUser = self.loggedUser(token);
        }
    };
    
    self.init = function(name){
        //if admin get data to populate serach, if !admin no data is needed
        if(self.loggedAdmin == true){
            //will have to pass real token. verify a user is really an admin and has access to all data
            self.dummyData = Users.getUsersTest("token");
        }else{
            //else nothing. current user (non-admin doesn't have access to other users)
        }
        //if we got to this function from the cancel click event
        if(name){
        //call userSet w/ current user  so the user doesn't have to select again
        self.userSet(name); 
        }else{
            //else call w/o params
            self.userSet();
        }

    };
    
    self.submit = function(){
        //validate fields and send to be
        //update on success      
        alert('submit');
    };
    
    self.reset = function(){
        //prompt to continue with reset
        bootbox.confirm("Are you sure you want to reset this profile?", function(result) {
            if(result == true){
                //for all keys in self.currentUSer
                for(var x in self.currentUser){
                    //if key is not user_name
                    if(x != 'user_name'){
                        $scope.$apply(function(){
                            //set value of key to empty
                            self.currentUser[x] = ' ';
                        });
                    };
                };
            };
            //else do nothing (user hit prompt in cancel)
        }); 
    };
    
    self.cancel = function(){
        //prompt to continue with reset
        bootbox.confirm("Are you sure you want to discard the changes?", function(result) {
            if(result == true){
                //call init to get user data from b.e again (reset fields to starting point)
                $scope.$apply(function(){
                    //pass in name so we know what to set the select field to 
                    self.init(self.currentUser.user_name);
                });
            };
        });
    };
    
    self.init();      
});
