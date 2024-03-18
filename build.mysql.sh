#!/bin/bash

./gradlew assemble pTML -b build.gradle 
./gradlew --no-daemon --stacktrace -b build.h2zero.mysql.gradle h2zero

