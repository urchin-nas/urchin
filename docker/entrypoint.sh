#!/bin/bash
cd /workspace

echo "[Starting Samba in preparation for tests]"
service samba start

echo "[Updating repository]"
git pull

echo "[Building application]"
mvn clean install
if [ $? -ne 0 ]; then
    echo "failed to build and test application, exiting..."
    exit 1
fi

echo "[Starting application]"
java -jar target/urchin-1.0-SNAPSHOT.jar
