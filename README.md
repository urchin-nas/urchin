# urchin
...

## Requirements

* [Ubuntu] (http://www.ubuntu.com/) (may work on other linux distros)
* [ecryptfs] (http://ecryptfs.org/)
* [mhddfs] (https://romanrm.net/mhddfs/)
* [samba] (https://www.samba.org/)
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

Go to http://localhost:8080

## Development

To start client using `webpack-dev-server` with Hot Module Replacement (HMR) support:
```
npm run dev
```

go to: http://localhost:3000/ (backend must be running to serve the api)

To start backend run `Application.java` or build the application and start it with java -jar command (see Build and Run)

### Swagger

http://localhost:8080/swagger-ui.html

## Build Server

[docker-build-server] (https://github.com/anhem/docker-build-server) contains a docker compose setup with all requirements to test and build application


