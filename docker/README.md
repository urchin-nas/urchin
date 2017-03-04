## Docker

Run Urchin in a docker container that will fetch `development` branch from github, build, test and start application. 

## Docker commands

Command examples for running development version of urchin in a docker container.

### Build
```bash
docker build -t urchin-dev .
```
### Run
Unix:
```bash
docker run -it --privileged --name urchin-dev -p 8080:8080 -v /data/urchin/.m2/:/root/.m2/ -v /data/urchin/node_modules:/workspace/node_modules urchin-dev
```
Windows (docker toolbox):
```bash
winpty docker run -it --privileged --name urchin-dev -p 8080:8080 -v //c/Users/**logged in user**/urchin/.m2:/root/.m2/ -v //c/Users/**logged in user**/urchin/node_modules:/workspace/node_modules urchin-dev
```

### Start existing container
```bash
docker start -ai urchin-dev
```

### Connect to container
```bash
docker exec -it urchin-dev /bin/bash
```