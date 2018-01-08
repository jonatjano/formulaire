#!/bin/bash

find src -type f -name "*.java" > sources.list

javac @sources.list @params.list
