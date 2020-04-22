#!/bin/bash


./gradlew assemble pTML -b build.gradle 
./gradlew -b build.h2zero.cockroach.gradle h2zero
./gradlew -b build.h2zero.mysql.gradle h2zero
./gradlew -b build.h2zero.postgresql.gradle h2zero


