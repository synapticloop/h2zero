#!/bin/bash

./gradlew assemble pTML -b build.gradle 
./gradlew --stacktrace -b build.h2zero.mysql.gradle h2zero

