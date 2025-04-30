#!/usr/bin/env bash
set -e

echo "Building and pushing messageSaverService..."
pushd messageSaverService > /dev/null
./gradlew clean shadowJar
docker build -t lolieshunter/messagesaver-service:latest .
docker push lolieshunter/messagesaver-service:latest
popd > /dev/null

echo "Building and pushing messageProcessorService..."
pushd messageProcessorService > /dev/null
./gradlew clean shadowJar
docker build -t lolieshunter/messageprocessor-service:latest .
docker push lolieshunter/messageprocessor-service:latest
popd > /dev/null

echo "Building and pushing botClient..."
pushd botClient > /dev/null
./gradlew clean shadowJar
docker build -t lolieshunter/bot-client:latest .
docker push lolieshunter/bot-client:latest
popd > /dev/null

echo "All services have been built and pushed successfully."
