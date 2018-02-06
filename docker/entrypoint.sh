#!/bin/bash

while getopts b:s: option
do
 case "${option}"
 in
 b) BRANCH=${OPTARG};;
 s) START=${OPTARG};;
 esac
done

cd /workspace

echo "[Remount / with ACL]"
mount -o remount,acl /

echo "[Starting Samba in preparation for tests]"
service samba start

if [ -n "${BRANCH}" ]; then
    echo "[Checking out branch ${BRANCH}]"
    git fetch
    git checkout ${BRANCH}
else
    echo "[Updating master]"
    git pull
fi
repository
echo "[Building application]"
mvn clean install
if [ $? -ne 0 ]; then
    echo "failed to build and test application, exiting..."
    exit 1
fi

if [ -n "${START}" ]; then
    echo "[Starting application]"
    java -jar target/urchin-1.0-SNAPSHOT.jar
else
	echo "Exiting..."
fi

