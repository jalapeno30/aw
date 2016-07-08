'use strict';

angular.module('lotteryApp')
  .service('Paypal', function Paypal() {
    
  	var self = this;

  	self.accessToken = '';
  	self.scope = '';

  	self.setAccessToken = function(accessToken) {
  		self.accessToken = accessToken;
  		console.log('Access Token: ' + accessToken);
  	}

  	self.getAccessToken = function() {
  		return self.accessToken;
  	}

  	self.setScope = function(scope) {
  		self.scope = scope.split("+");
  		console.log('Scope: ' + self.scope);
  	}

  	self.getScope = function() {
  		return self.scope;
  	}

  });
