#!/bin/bash

BRANCH=master
START=false
ANALYZE=false

while getopts b:s:a: option
do
 case "${option}"
 in
 b) BRANCH=${OPTARG};;
 s) START=${OPTARG};;
 a) ANALYZE=${OPTARG};;
 esac
done

cd /workspace

echo "[Remount / with ACL]"
mount -o remount,acl /

echo "[Starting Samba in preparation for tests]"
service samba start

if [ ${BRANCH} != "master" ]; then
    echo "[Checking out branch '${BRANCH}']"
    git fetch
    git checkout ${BRANCH}
else
    echo "[Updating '${BRANCH}']"
    git pull
fi

if [ ${ANALYZE} = true ]; then
    echo "[Building application and analyzing code]"
    mvn clean install sonar:sonar -Psonar \
    -Dsonar.branch.name=${BRANCH}
else
    echo "[Building application]"
    mvn clean install
fi

if [ $? -ne 0 ]; then
    echo "failed to build and test application, exiting..."
    exit 1
fi

if [ ${START} = true ]; then
    echo "[Starting application]"
    java -jar target/urchin-1.0-SNAPSHOT.jar
else
	echo "Exiting..."
fi

