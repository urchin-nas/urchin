# urchin

[![Build Status](https://travis-ci.org/anhem/urchin.svg?branch=master)](https://travis-ci.org/anhem/urchin)
[![Quality](https://sonarcloud.io/api/project_badges/measure?project=urchin%3Aurchin&metric=alert_status)](https://sonarcloud.io/dashboard?id=urchin%3Aurchin)

This project is built primarily on **Ubuntu** (16.04). Even though the backend is written in Java, it is tightly coupled with the underlying operating system and will not work on Windows or OS X machines. Some of the requirements are listed below. 

For development on non-supported platforms, run everything on Ubuntu using a virtualization software such as [VirtualBox](https://www.virtualbox.org/).

## Requirements

* [Ubuntu](http://www.ubuntu.com/) (may work on other linux distros)
* [ecryptfs](http://ecryptfs.org/)
* [mhddfs](https://romanrm.net/mhddfs/)
* [samba](https://www.samba.org/)

Installation requirements using apt:
```
apt install ecryptfs-utils mhddfs samba samba-common-bin
```

## Permissions 

Either run everything as `root` 
or copy content from `sudoers.d/urchin` to `visudo -f /etc/sudoers.d/urchin` and change the username if necessary

## Build

Build frontend and backend into a self-executable jar:
```bash
mvn install
```

## Run
```bash
java -jar urchin-1.0-SNAPSHOT.jar
```

Go to http://localhost:8080

## Docker

More information [here](docker/README.md)

## Development

To start client using `webpack-dev-server`:
```bash
npm start
```

go to: http://localhost:3000/ (backend must be running to serve the api)

To start backend run `Application.java` or build the application and start it with java -jar command (see Build and Run)

### Swagger

http://localhost:8080/swagger-ui.html

## Build Server

[docker-build-server](https://github.com/anhem/docker-build-server) contains a docker compose setup with all requirements to test and build application

