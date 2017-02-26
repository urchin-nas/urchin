# urchin
...

## Requirements

* [Ubuntu] (http://www.ubuntu.com/) (may work on other linux distros)
* [ecryptfs] (http://ecryptfs.org/)
* [mhddfs] (https://romanrm.net/mhddfs/)
* root access

## Build

Build frontend and backend into a self-executable jar:
```
mvn install
```

## Run
```
java -jar urchin-1.0-SNAPSHOT.jar
```

## Development

To start client using `webpack-dev-server` with Hot Module Replacement (HMR) support:
```
npm run start
```
## Build Server

[docker-build-server] (https://github.com/anhem/docker-build-server) contains a docker compose setup with all requirements to test and build application


