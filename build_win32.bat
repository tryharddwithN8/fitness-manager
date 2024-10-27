@echo off

clear || cls

echo "Starting project with maven.."

mvn clean -B javafx:run > output.log 2>&1

echo "Done !"
