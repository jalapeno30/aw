'use strict';

angular.module('lotteryApp')
  .service('Users', function Users($http, ENV) {
    var self = this;

    self.users = [];
    self.statuses = [];
    self.roles = [];

    self.refreshUsers = function() {
      $http.get(ENV.apiEndpoint + '/authentication/usersList')
      .success(function(data){
        self.users = [];
        for (var i = 0; i < data.length; i++) {
          self.users.push({
            "id" : data[i].userId,
            "username" : data[i].userName,
            "role" : data[i].role,
            "status" : data[i].status
          });
        }
      })
      .error(function(data){
        console.log(data);
      });
    };

    self.refreshStatusList = function() {
      $http.get(ENV.apiEndpoint + '/authentication/statusList')
      .success(function(data){
        self.statuses = data;
      })
      .error(function(data){
        console.log(data);
      });
    }

    self.refreshRolesList = function() {
      $http.get(ENV.apiEndpoint + '/authentication/rolesList')
      .success(function(data){
        self.roles = data;
      })
      .error(function(data){
        console.log(data);
      });
    }

    self.getUsers = function(token) {
        $http({
            method: 'GET',
            url: '/someUrl',
            params: {
                token: token
            }
        }).then(function successCallback(response) {
            self.users = response.data;
        }, function errorCallback(response) {
            //this is bad
        });
    };
    
    self.getUsersTest = function(token) {
        if(token){
            var dummyData =  [{
  "account_number": 5927,
  "address_1": "1 Dtreet st",
  "birth_date": "8/8/1986",
  "city": "Aurora",
  "country": "United States",
  "email_id": "jcook0@seesaa.net",
  "first_name": "Jean",
  "gender": "F",
  "last_name": "Cook",
  "password": "0IbA1r",
  "phone_number": "1-(303)911-2902",
  "role_name": "Administrator",
  "state": "Colorado",
  "user_name": "Opela",
  "zipcode": "80044"
}, {
  "account_number": 5939,
  "birth_date": "4/16/1969",
  "city": "Huntington",
  "country": "United States",
  "email_id": "bjordan1@tamu.edu",
  "first_name": "Bruce",
  "gender": "M",
  "last_name": "Jordan",
  "password": "5VMuTM",
  "phone_number": "1-(304)860-6435",
  "role_name": "Customer Support Representative",
  "state": "West Virginia",
  "user_name": "Lotlux",
  "address_1": "1 Dtreet st",
  "zipcode": "25711"
}, {
  "account_number": 8316,
  "birth_date": "3/30/1921",
  "city": "Alpharetta",
  "country": "United States",
  "email_id": "lbennett2@phoca.cz",
  "first_name": "Lisa",
  "gender": "F",
  "last_name": "Bennett",
  "password": "vIyBsSEDlc0w",
  "phone_number": "1-(770)644-3075",
  "role_name": "User",
  "state": "Georgia",
  "address_1": "1 Dtreet st",
  "user_name": "Greenlam",
  "zipcode": "30022"
}, {
  "account_number": 7426,
  "birth_date": "3/24/1965",
  "address_1": "1 Dtreet st",
  "city": "Humble",
  "country": "United States",
  "email_id": "kpeters3@sohu.com",
  "first_name": "Kevin",
  "gender": "M",
  "last_name": "Peters",
  "password": "RgkUGULwRsBd",
  "phone_number": "1-(713)260-0145",
  "role_name": "User",
  "state": "Texas",
  "user_name": "Toughjoyfax",
  "zipcode": "77346"
}, {
  "account_number": 6934,
  "address_1": "1 Dtreet st",
  "birth_date": "6/16/1975",
  "city": "Washington",
  "country": "United States",
  "email_id": "smills5@google.com.au",
  "first_name": "Sarah",
  "gender": "F",
  "last_name": "Mills",
  "password": "Q2rYCglK",
  "phone_number": "1-(202)166-8746",
  "role_name": "Administrator",
  "state": "District of Columbia",
  "user_name": "Zamit",
  "zipcode": "20503"
}, {
  "account_number": 6394,
  "address_1": "1 Dtreet st",
  "birth_date": "10/1/1932",
  "city": "Sacramento",
  "country": "United States",
  "email_id": "jwillis6@parallels.com",
  "first_name": "Jason",
  "gender": "M",
  "last_name": "Willis",
  "password": "NdugjBaY",
  "phone_number": "1-(530)109-0168",
  "role_name": "Administrator",
  "state": "California",
  "user_name": "Tin",
  "zipcode": "95833"
}, {
  "account_number": 1067,
  "birth_date": "8/12/1941",
  "city": "Birmingham",
  "address_1": "1 Dtreet st",
  "country": "United States",
  "email_id": "sbowman7@google.it",
  "first_name": "Steve",
  "gender": "M",
  "last_name": "Bowman",
  "password": "2nLnxT33O",
  "phone_number": "1-(205)416-2805",
  "role_name": "Administrator",
  "state": "Alabama",
  "user_name": "Otcom",
  "zipcode": "35285"
}, {
  "account_number": 2259,
  "address_1": "1 Dtreet st",
  "birth_date": "6/30/1941",
  "city": "Greenville",
  "country": "United States",
  "email_id": "smurphy8@wisc.edu",
  "first_name": "Shawn",
  "gender": "M",
  "last_name": "Murphy",
  "password": "lGsJ1eAAZlp",
  "phone_number": "1-(864)512-5036",
  "role_name": "User",
  "state": "South Carolina",
  "user_name": "Daltfresh",
  "zipcode": "29610"
}, {
  "account_number": 2331,
  "address_1": "1 Dtreet st",
  "birth_date": "5/3/1954",
  "city": "Lexington",
  "country": "United States",
  "email_id": "tvasquez9@time.com",
  "first_name": "Tammy",
  "gender": "F",
  "last_name": "Vasquez",
  "password": "dVo4xMZo0",
  "phone_number": "1-(859)428-1216",
  "role_name": "User",
  "state": "Kentucky",
  "user_name": "Transcof",
  "zipcode": "40505"
}, {
  "account_number": 3629,
  "birth_date": "6/17/1982",
  "city": "New York City",
  "country": "United States",
  "address_1": "1 Dtreet st",
  "email_id": "jfishera@hibu.com",
  "first_name": "Jacqueline",
  "gender": "F",
  "last_name": "Fisher",
  "password": "hRJJ3BC",
  "phone_number": "1-(212)322-4730",
  "role_name": "User",
  "state": "New York",
  "user_name": "Domainer",
  "zipcode": "10275"
}, {
  "account_number": 1229,
  "birth_date": "12/1/1980",
  "address_1": "1 Dtreet st",
  "city": "Oakland",
  "country": "United States",
  "email_id": "jmooreb@berkeley.edu",
  "first_name": "Jonathan",
  "gender": "M",
  "last_name": "Moore",
  "password": "fiZGEodVebz",
  "phone_number": "1-(415)568-2570",
  "role_name": "Customer Support Representative",
  "state": "California",
  "user_name": "Zontrax",
  "zipcode": "94616"
}, {
  "account_number": 5166,
  "birth_date": "6/19/1952",
  "city": "Virginia Beach",
  "country": "United States",
  "email_id": "bwilsonc@exblog.jp",
  "first_name": "Benjamin",
  "gender": "M",
  "last_name": "Wilson",
  "password": "qVpKpsWaJ6",
  "phone_number": "1-(757)384-2868",
  "role_name": "Administrator",
  "state": "Virginia",
  "user_name": "Subin",
  "zipcode": "23459"
}, {
  "account_number": 6378,
  "birth_date": "10/26/1934",
  "city": "Houston",
  "address_1": "1 Dtreet st",
  "country": "United States",
  "email_id": "rcookd@arstechnica.com",
  "first_name": "Rose",
  "gender": "F",
  "last_name": "Cook",
  "password": "TThstznvgY",
  "phone_number": "1-(281)910-9574",
  "role_name": "Customer Support Representative",
  "state": "Texas",
  "user_name": "Mat Lam Tam",
  "zipcode": "77035"
}, {
  "account_number": 1896,
  "birth_date": "3/11/1937",
  "address_1": "1 Dtreet st",
  "city": "Lynchburg",
  "country": "United States",
  "email_id": "sdavise@disqus.com",
  "first_name": "Scott",
  "gender": "M",
  "last_name": "Davis",
  "password": "qM7qkmvC",
  "phone_number": "1-(434)274-8357",
  "role_name": "Administrator",
  "state": "Virginia",
  "user_name": "Voltsillam",
  "zipcode": "24503"
}, {
  "account_number": 9072,
  "address_1": "1 Dtreet st",
  "birth_date": "6/11/1972",
  "city": "Lansing",
  "country": "United States",
  "email_id": "rhowardg@oracle.com",
  "first_name": "Raymond",
  "gender": "M",
  "last_name": "Howard",
  "password": "Qn2GIuHRD9C",
  "phone_number": "1-(517)381-1430",
  "role_name": "User",
  "state": "Michigan",
  "user_name": "Wrapsafe",
  "zipcode": "48930"
}, {
  "account_number": 2581,
  "address_1": "1 Dtreet st",
  "birth_date": "9/5/1928",
  "city": "Philadelphia",
  "country": "United States",
  "email_id": "jkelleyi@hugedomains.com",
  "first_name": "Julia",
  "gender": "F",
  "last_name": "Kelley",
  "password": "pc851yFfOU",
  "phone_number": "1-(215)969-9847",
  "role_name": "Customer Support Representative",
  "state": "Pennsylvania",
  "user_name": "Flowdesk",
  "zipcode": "19115"
}, {
  "account_number": 7016,
  "birth_date": "11/24/1956",
  "city": "Memphis",
  "country": "United States",
  "email_id": "wfernandezj@etsy.com",
  "address_1": "1 Dtreet st",
  "first_name": "Wanda",
  "gender": "F",
  "last_name": "Fernandez",
  "password": "45YGnXEZpPS",
  "phone_number": "1-(615)776-1016",
  "role_name": "Administrator",
  "state": "Tennessee",
  "user_name": "Sub-Ex",
  "zipcode": "38119"
},{
  "account_number": 7107,
  "birth_date": "9/10/1929",
  "city": "Pompano Beach",
  "address_1": "1 Dtreet st",
  "country": "United States",
  "email_id": "cgreene0@tripod.com",
  "first_name": "Catherine",
  "gender": "F",
  "last_name": "Greene",
  "password": "SSyoDi1rK",
  "phone_number": "1-(754)877-5044",
  "role_name": "Administrator",
  "state": "Florida",
  "user_name": "Ventosanzap",
  "zipcode": "33064"
}, {
  "account_number": 5186,
  "birth_date": "7/17/1983",
  "city": "Topeka",
  "address_1": "1 Dtreet st",
  "country": "United States",
  "email_id": "ccarr1@google.com.au",
  "first_name": "Christina",
  "gender": "F",
  "last_name": "Carr",
  "password": "7d50eL",
  "phone_number": "1-(785)799-0951",
  "role_name": "Customer Support Representative",
  "state": "Kansas",
  "user_name": "Zathin",
  "zipcode": "66699"
}, {
  "account_number": 1776,
  "birth_date": "3/13/1949",
  "address_1": "1 Dtreet st",
  "city": "Lincoln",
  "country": "United States",
  "email_id": "wkennedy2@parallels.com",
  "first_name": "Wayne",
  "gender": "M",
  "last_name": "Kennedy",
  "password": "B00MyurUvYN",
  "phone_number": "1-(402)211-2905",
  "role_name": "Administrator",
  "state": "Nebraska",
  "user_name": "Sonair",
  "zipcode": "68517"
}, {
  "account_number": 9419,
  "birth_date": "6/6/1941",
  "city": "Daytona Beach",
  "country": "United States",
  "email_id": "rstone4@about.com",
  "address_1": "1 Dtreet st",
  "first_name": "Ralph",
  "gender": "M",
  "last_name": "Stone",
  "password": "LmrsAZEn",
  "phone_number": "1-(386)292-3954",
  "role_name": "Customer Support Representative",
  "state": "Florida",
  "user_name": "Prodder",
  "zipcode": "32128"
}, {
  "account_number": 9427,
  "birth_date": "6/3/1926",
  "city": "Cleveland",
  "country": "United States",
  "address_1": "1 Dtreet st",
  "email_id": "alawrence5@qq.com",
  "first_name": "Alan",
  "gender": "M",
  "last_name": "Lawrence",
  "password": "vpXbspD",
  "phone_number": "1-(216)451-6029",
  "role_name": "Administrator",
  "state": "Ohio",
  "user_name": "Viva",
  "zipcode": "44191"
}, {
  "account_number": 9638,
  "birth_date": "12/31/1978",
  "city": "Boise",
  "country": "United States",
  "address_1": "1 Dtreet st",
  "email_id": "akelly6@sphinn.com",
  "first_name": "Antonio",
  "gender": "M",
  "last_name": "Kelly",
  "password": "TzlJeGFiMn",
  "phone_number": "1-(208)890-4669",
  "role_name": "Customer Support Representative",
  "state": "Idaho",
  "user_name": "Duobam",
  "zipcode": "83716"
}, {
  "account_number": 2621,
  "birth_date": "3/4/1985",
  "city": "Kansas City",
  "country": "United States",
  "email_id": "kyoung7@china.com.cn",
  "first_name": "Keith",
  "gender": "M",
  "last_name": "Young",
  "password": "Rv5teMIPLUW",
  "address_1": "1 Dtreet st",
  "phone_number": "1-(816)164-2676",
  "role_name": "Administrator",
  "state": "Missouri",
  "user_name": "Zaam-Dox",
  "zipcode": "64109"
}, {
  "account_number": 1452,
  "birth_date": "9/30/1949",
  "city": "Shawnee Mission",
  "address_1": "1 Dtreet st",
  "country": "United States",
  "email_id": "candrews9@drupal.org",
  "first_name": "Charles",
  "gender": "M",
  "last_name": "Andrews",
  "password": "XaMcml4",
  "phone_number": "1-(816)751-7401",
  "role_name": "Administrator",
  "state": "Kansas",
  "user_name": "Cookley",
  "zipcode": "66210"
}, {
  "account_number": 6276,
  "birth_date": "10/13/1945",
  "city": "Amarillo",
  "country": "United States",
  "email_id": "tharpera@qq.com",
  "first_name": "Tammy",
  "address_1": "1 Dtreet st",
  "gender": "F",
  "last_name": "Harper",
  "password": "LXVUR5",
  "phone_number": "1-(806)924-2766",
  "role_name": "User",
  "state": "Texas",
  "user_name": "Solarbreeze",
  "zipcode": "79176"
}, {
  "account_number": 9284,
  "birth_date": "11/28/1920",
  "city": "Miami",
  "country": "United States",
  "email_id": "mfergusonb@furl.net",
  "address_1": "1 Dtreet st",
  "first_name": "Melissa",
  "gender": "F",
  "last_name": "Ferguson",
  "password": "BqGHRawPr",
  "phone_number": "1-(305)775-5611",
  "role_name": "User",
  "state": "Florida",
  "user_name": "Trippledex",
  "zipcode": "33283"
}, {
  "account_number": 2745,
  "birth_date": "6/4/1971",
  "city": "Irvine",
  "country": "United States",
  "email_id": "ahalld@youtube.com",
  "first_name": "Aaron",
  "address_1": "1 Dtreet st",
  "gender": "M",
  "last_name": "Hall",
  "password": "xFg4l93nKX",
  "phone_number": "1-(714)973-3887",
  "role_name": "User",
  "state": "California",
  "user_name": "Cardify",
  "zipcode": "92717"
}, {
  "account_number": 4911,
  "birth_date": "6/26/1943",
  "city": "Sandy",
  "country": "United States",
  "email_id": "lgreenee@free.fr",
  "address_1": "1 Dtreet st",
  "first_name": "Lori",
  "gender": "F",
  "last_name": "Greene",
  "password": "Q5eiMImuH",
  "phone_number": "1-(801)235-5836",
  "role_name": "User",
  "state": "Utah",
  "user_name": "Treeflex",
  "zipcode": "84093"
}, {
  "account_number": 3998,
  "birth_date": "6/27/1932",
  "city": "Philadelphia",
  "country": "United States",
  "email_id": "rwilsonf@tinyurl.com",
  "first_name": "Rebecca",
  "gender": "F",
  "last_name": "Wilson",
  "address_1": "1 Dtreet st",
  "password": "RMeSViiQ23",
  "phone_number": "1-(610)398-6421",
  "role_name": "Administrator",
  "state": "Pennsylvania",
  "user_name": "Overhold",
  "zipcode": "19120"
}, {
  "account_number": 4653,
  "birth_date": "10/1/1929",
  "city": "Mesa",
  "address_1": "1 Dtreet st",
  "country": "United States",
  "email_id": "jphillipsg@sogou.com",
  "first_name": "Jerry",
  "gender": "M",
  "last_name": "Phillips",
  "password": "QZXtWXp",
  "phone_number": "1-(928)439-2739",
  "role_name": "Administrator",
  "state": "Arizona",
  "user_name": "Voyatouch",
  "zipcode": "85210"
}, {
  "account_number": 4096,
  "birth_date": "9/28/1946",
  "city": "Indianapolis",
  "country": "United States",
  "email_id": "wchavezh@wikimedia.org",
  "first_name": "Wanda",
  "address_1": "1 Dtreet st",
  "gender": "F",
  "last_name": "Chavez",
  "password": "QnicjTfec8",
  "phone_number": "1-(317)738-4474",
  "role_name": "Customer Support Representative",
  "state": "Indiana",
  "user_name": "Holdlamis",
  "zipcode": "46295"
}, {
  "account_number": 4020,
  "birth_date": "11/11/1922",
  "city": "Sandy",
  "country": "United States",
  "address_1": "1 Dtreet st",
  "email_id": "wmilleri@moonfruit.com",
  "first_name": "Wanda",
  "gender": "F",
  "last_name": "Miller",
  "password": "jmN5aaOmSx",
  "phone_number": "1-(801)618-9754",
  "role_name": "User",
  "state": "Utah",
  "user_name": "Alpha",
  "zipcode": "84093"
}, {
  "account_number": 2657,
  "birth_date": "11/28/1946",
  "address_1": "1 Dtreet st",
  "city": "Indianapolis",
  "country": "United States",
  "email_id": "rreidj@smugmug.com",
  "first_name": "Ruby",
  "gender": "F",
  "last_name": "Reid",
  "password": "G0tYJuxo",
  "phone_number": "1-(317)508-3868",
  "role_name": "User",
  "state": "Indiana",
  "user_name": "Kanlam",
  "zipcode": "46295"
}];
            return dummyData;
        }
        else return "ERRORRRRRRR"
    }
    
    self.getStatuses = function() {
      return self.statuses;
    }

    self.getRoles = function() {
      return self.roles;
    }

    self.changeStatus = function(id, status) {
      $http({
        url: ENV.apiEndpoint + '/authentication/changeStatus',
        method: 'GET',
        params: {
          "userId" : id,
          "statusId" : status
        }
      })
      .success(function(data){
        // console.log(data);
        self.refreshUsers();
      })
      .error(function(data){
        // console.log(data);
      });
    }

    self.changeRole = function(id, role) {
      $http({
        url: ENV.apiEndpoint + '/authentication/changeRole',
        method: 'GET',
        params: {
          "userId" : id,
          "roleId" : role
        }
      })
      .success(function(data){
        // console.log(data);
        self.refreshUsers();
      })
      .error(function(data){
        // console.log(data);
      });
    }

    self.refreshUsers();
    self.refreshStatusList();
    self.refreshRolesList();

  });
