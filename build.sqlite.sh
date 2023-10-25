#!/bin/bash

./gradlew assemble pTML -b build.gradle 
./gradlew --no-daemon -b build.h2zero.sqlite3.gradle h2zero

