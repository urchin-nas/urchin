#!/bin/bash
cd /workspace

echo "[Remount / with ACL]"
mount -o remount,acl /

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

if [ "${1,,}" == "start" ]; then
    echo "[Starting application]"
    java -jar target/urchin-1.0-SNAPSHOT.jar
else
	echo "Exiting..."
fi

