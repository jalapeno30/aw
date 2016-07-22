# ILTS Lottery Web App #
This repository contains the frontend code for the ILTS Lottery Web App. It is written in Javascript using AngularJS.

## Pre-requisites ##
To setup this project, you would need a copy of [nodejs](https://nodejs.org/en/) installed in your dev environment.

## Installation ##
After cloning the project, navigate to the project root and install the node packages by running.

```
#!bash

npm install
```

This project uses bower to manage the front-end dependencies. To install the bower packages, run 

```
#!bash

bower install
```

## Grunt ##
This project uses the grunt taskrunner to perform several tasks. First, you need to install grunt-cli onto your dev environment.

```
#!bash

npm install -g grunt-cli
```

You can then run the following commands:
```
#!bash

# prepare a distribution for deployment
grunt build
# run a development server on 0.0.0.0:9002
grunt serve
```


