#!/bin/bash

./gradlew --no-daemon assemble pTML -b build.gradle 
./gradlew --no-daemon -b build.h2zero.mysql.gradle h2zero

