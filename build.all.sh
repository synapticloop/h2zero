#!/bin/bash


./gradlew assemble pTML -b build.gradle 
./gradlew --no-daemon -b build.h2zero.cockroach.gradle h2zero
./gradlew --no-daemon -b build.h2zero.mysql.gradle h2zero
./gradlew --no-daemon -b build.h2zero.postgresql.gradle h2zero
./gradlew --no-daemon -b build.h2zero.sqlite3.gradle h2zero


