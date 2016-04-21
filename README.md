# urchin
...

## Requirements

* [Ubuntu] (http://www.ubuntu.com/) (may work on other linux distros)
* root access (for ecryptfs and mhddfs)
* [ecryptfs] (http://ecryptfs.org/)
* [mhddfs] (https://romanrm.net/mhddfs/)

## Build
```
mvn install
```
It is possible to build application on windows and Mac. Integration tests that require `ecryptfs` and `mhddfs` will be ignored.

## Run
```
java -jar urchin-1.0-SNAPSHOT.jar
```

## Build Server

[docker-build-server] (https://github.com/anhem/docker-build-server) contains a docker compose setup with all requirements to test and build application


