# Docker commands

## Build
```bash
docker build -t urchin-dev .
```
## Run
```bash
docker run -it --privileged --name=urchin-dev urchin-dev -p 8080:80
```