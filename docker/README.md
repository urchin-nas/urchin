# Docker commands

Command examples for running development version of urchin i a docker container.

## Build
```bash
docker build -t urchin-dev .
```
## Run
```bash
docker run -it --privileged --name urchin-dev -p 8080:8080 -v /data/urchin/.m2/:/root/.m2/ -v /data/urchin/node_modules:/workspace/node_modules urchin-dev
```

## Start existing container
```bash
docker start -ai urchin-dev
```

## Connect to container
```bash
docker exec -it urchin-dev /bin/bash
```