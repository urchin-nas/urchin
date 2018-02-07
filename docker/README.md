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

```bash
docker run -it --privileged --name urchin-dev -p 8080:8080 urchin-dev
```
#### Options

|flag|options|Description|
| --- | --- | --- |
| -b | branch-name | name of branch to build |
| -s | true | start application after build |
| -a | true | analyze code and upload results to [sonarcloud](https://sonarcloud.io/dashboard?id=urchin%3Aurchin) |

Example:

```bash
docker run -it --privileged --name urchin-dev -p 8080:8080 urchin-dev -b travis -s true
```

### Start existing container
```bash
docker start -ai urchin-dev
```

### Connect to container
```bash
docker exec -it urchin-dev /bin/bash
```
