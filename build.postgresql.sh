#!/bin/bash

./gradlew assemble pTML -b build.gradle 
./gradlew -b build.h2zero.postgresql.gradle h2zero

