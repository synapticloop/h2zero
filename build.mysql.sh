#!/bin/bash

./gradlew assemble pTML -b build.gradle 
./gradlew -b build.h2zero.mysql.gradle h2zero

