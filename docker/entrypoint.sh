#!/bin/bash
cd /workspace

echo "[Updating repository]"
git pull

echo "[Building application]"
mvn clean install

echo "[Running application]"
java -jar target/urchin-1.0-SNAPSHOT.jar
