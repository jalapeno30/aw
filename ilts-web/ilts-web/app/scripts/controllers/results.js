'use strict';

angular.module('lotteryApp')
  .controller('ResultsCtrl', function ($scope) {
    $scope.games = [
    	{
    		"name" : "Grand Lotto 6/55",
    		"logo" : "images/ignore/grand-lotto.jpg",
    		"results" : [
    			{
    				"date" : "April 16, 2014",
    				"result" : "28-41-12-29-11-01"
    			},
    			{
    				"date" : "April 14, 2014",
    				"result" : "02-24-06-17-11-14"
    			},
    			{
    				"date" : "April 12, 2014",
    				"result" : "16-13-30-41-44-46"
    			},
    			{
    				"date" : "April 9, 2014",
    				"result" : "52-15-25-55-23-31"
    			},
    			{
    				"date" : "April 7, 2014",
    				"result" : "39-21-42-28-05-27"
    			}
    		]
    	},
    	{
    		"name" : "Super Lotto 6/49",
    		"logo" : "images/ignore/superloto-banner.jpg",
    		"results" : [
    			{
    				"date" : "April 16, 2014",
    				"result" : "28-41-12-29-11-01"
    			},
    			{
    				"date" : "April 14, 2014",
    				"result" : "02-24-06-17-11-14"
    			},
    			{
    				"date" : "April 12, 2014",
    				"result" : "16-13-30-41-44-46"
    			},
    			{
    				"date" : "April 9, 2014",
    				"result" : "52-15-25-55-23-31"
    			},
    			{
    				"date" : "April 7, 2014",
    				"result" : "39-21-42-28-05-27"
    			}
    		]
    	},
    	{
    		"name" : "Mega Lotto 6/45",
    		"logo" : "images/ignore/megaloto-banner.jpg",
    		"results" : [
    			{
    				"date" : "April 16, 2014",
    				"result" : "28-41-12-29-11-01"
    			},
    			{
    				"date" : "April 14, 2014",
    				"result" : "02-24-06-17-11-14"
    			},
    			{
    				"date" : "April 12, 2014",
    				"result" : "16-13-30-41-44-46"
    			},
    			{
    				"date" : "April 9, 2014",
    				"result" : "52-15-25-55-23-31"
    			},
    			{
    				"date" : "April 7, 2014",
    				"result" : "39-21-42-28-05-27"
    			}
    		]
    	},
    	{
    		"name" : "Lotto 6/42",
    		"logo" : "images/ignore/loto-banner.jpg",
    		"results" : [
    			{
    				"date" : "April 16, 2014",
    				"result" : "28-41-12-29-11-01"
    			},
    			{
    				"date" : "April 14, 2014",
    				"result" : "02-24-06-17-11-14"
    			},
    			{
    				"date" : "April 12, 2014",
    				"result" : "16-13-30-41-44-46"
    			},
    			{
    				"date" : "April 9, 2014",
    				"result" : "52-15-25-55-23-31"
    			},
    			{
    				"date" : "April 7, 2014",
    				"result" : "39-21-42-28-05-27"
    			}
    		]
    	}
    ];
  });
