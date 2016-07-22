'use strict';

angular.module('lotteryApp')
  .controller('HomeCtrl', function ($scope) {

    $scope.announcements = [
    	{
    		"title" : 'PCSO gives Bora an ambulance',
    		"featureImage" : 'images/ignore/lottery1.png',
    		"content" : 'The Philippine Charity Sweepstakes Office (PCSO) donated an ambulance to the Boracay Fire Rescue Ambulance Volunteers (BFRAV), the first responders of the Boracay Action Group (BAG).'
    	},
    	{
    		"title" : 'PCSO goes to Batangas',
    		"featureImage" : 'images/ignore/lottery1.png',
    		"content" : 'Sto. Tomas, Batangas celebrated its town fiesta on Monday, March 3, highlighted by the inauguration of its mobile clinic and Klinika ng Bayan.'
    	},
    	{
    		"title" : 'PCSO reaches out to Samar',
    		"featureImage" : 'images/ignore/lottery1.png',
    		"content" : 'Philippine Charity Sweepstakes Office (PCSO), through its Chairman Margie Juico, turned over a P2-million donation in check to Mayor Ezekiel Fritz Aseo of the Municipality of San Julian'
    	},
    	{
    		"title" : 'Palawan receives PCSO gift',
    		"featureImage" : 'images/ignore/lottery1.png',
    		"content" : 'It’s a bit late for a Christmas gift, but Palawan government officials happily accepted the “Pamaskong Alay” from the Philippine Charity Sweepstakes Office (PCSO).'
    	}
    ];

    $scope.results = [
        {
            "logo" : "images/ignore/grand-lotto.jpg",
            "result" : "02-24-06-17-11-14",
            "prize" : "30,000,000.00",
            "date" : "2014-04-14",
            "winners" : "0" 
        },
        {
            "logo" : "images/ignore/megaloto-banner.jpg",
            "result" : "02-24-06-17-11-14",
            "prize" : "30,000,000.00",
            "date" : "2014-04-14",
            "winners" : "0" 
        },
        {
            "logo" : "images/ignore/superloto-banner.jpg",
            "result" : "02-24-06-17-11-14",
            "prize" : "30,000,000.00",
            "date" : "2014-04-14",
            "winners" : "0" 
        },
        {
            "logo" : "images/ignore/loto-banner.jpg",
            "result" : "02-24-06-17-11-14",
            "prize" : "30,000,000.00",
            "date" : "2014-04-14",
            "winners" : "0" 
        }
    ];
  });
