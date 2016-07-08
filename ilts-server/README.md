# ILTS Lottery App #

## Installation ##

### Prerequisites ###
To setup the development environment, you would need [vagrant](https://www.vagrantup.com/downloads.html) and [VirtualBox](https://www.virtualbox.org/wiki/Downloads) installed.

### Vagrant ###
To get vagrant up and running, navigate to the app root and run:

```
#!bash

vagrant up
```
This will create the VM dev environment and install the necessary dependencies.

### Gradle ###
This app uses gradle as a build script. To run

```
#!bash

vagrant ssh
cd /vagrant
./gradlew build
```



## Running the Service ##

```
#!bash

java -jar build/libs/lottery-service-<version>.jar
```