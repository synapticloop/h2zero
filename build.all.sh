#!/bin/bash

./gradlew assemble pTML -b build.gradle

./gradlew --no-daemon -b build.h2zero.all.gradle h2zero


