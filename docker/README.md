## Docker

Run Urchin in a docker container that will fetch `development` branch from github, build, test and optionaly start application. 

When container is started goto <http://localhost:8080/>

## Docker commands

Command examples for running development version of urchin in a docker container.

### Build
```bash
docker build -t urchin-dev .
```
### Run
Unix:
```bash
docker run -it --privileged --name urchin-dev -p 8080:8080 urchin-dev start
```
_Omit `start` from the command if you only want to build and test._

### Start existing container
```bash
docker start -ai urchin-dev
```

### Connect to container
```bash
docker exec -it urchin-dev /bin/bash
```
