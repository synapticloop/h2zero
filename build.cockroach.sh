#!/bin/bash

./gradlew assemble pTML -b build.gradle 
./gradlew -b build.h2zero.cockroach.gradle h2zero

