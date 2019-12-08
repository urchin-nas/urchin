## Docker

Run Urchin in a docker container that will fetch specified branch from github, build, test and optionaly start application. 

When container is started goto <http://localhost:8080/>

## Docker commands

Command examples for running development version of urchin in a docker container.

### Build
```bash
docker build -t urchin-dev .
```
### Run

```bash
docker run -it --privileged --cap-add=LINUX_IMMUTABLE --name urchin-dev -p 8080:8080 urchin-dev
```
#### Options

|flag|options|Description|
| --- | --- | --- |
| -b | branch-name | name of branch to build (default: master) |
| -s | true | start application after build (default: false) |
| -a | true | analyze code and upload results to [sonarcloud](https://sonarcloud.io/dashboard?id=urchin%3Aurchin) (default: false) |

Example:

```bash
docker run -it --privileged --cap-add LINUX_IMMUTABLE --name urchin-dev -p 8080:8080 urchin-dev -b my-branch -s true
```

### Start existing container
```bash
docker start -ai urchin-dev
```

### Connect to container
```bash
docker exec -it urchin-dev /bin/bash
```
