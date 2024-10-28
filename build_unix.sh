#!/bin/bash

chmod +x *.sh

clear && mvn -B clean javafx:run > output.log 2>&1 